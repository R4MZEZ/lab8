package Main;

import tools.ServerLogger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerStart {
    static int PORT;
    static final HashMap<InetSocketAddress,Connector> users = new HashMap<>();
    static final String jdbcURL = "jdbc:postgresql://localhost:3125/studs";
//    static final String jdbcURL = "jdbc:postgresql://pg:5432/studs";
    static DatabaseHandler databaseHandler;
    static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Scanner scanner = new Scanner(System.in);
        String login = "";
        String password = "";

        try {
            scanner = new Scanner(new FileReader("C:\\Users\\User\\Desktop\\прога\\lab6\\server\\src\\main\\resources\\credentials.txt"));
        } catch (FileNotFoundException ex) {
            System.err.println("Не найден файл с данными для входа. Завершение работы.");
            System.exit(-1);
        }
        try {
            login = scanner.nextLine().trim();
            password = scanner.nextLine().trim();
        } catch (NoSuchElementException ex) {
            System.err.println("Не найдены данные для входа. Завершение работы.");
            System.exit(-1);
        }

        databaseHandler= new DatabaseHandler(jdbcURL, login, password);

        try {
            databaseHandler.connectToDatabase();
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных. Завершение работы.");
            System.exit(-1);
        }

        addShutdownHook();

        readPort();

        byte[] buffer;
        try {
            DatagramSocket datagramSocket = new DatagramSocket(PORT);
            ServerLogger.logger.info("Запуск сервера на порту {}", PORT);
            System.out.println("Ожидание подключения...");

            while (true) {
                buffer = new byte[65536];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                executor.execute(new Reciever(packet,datagramSocket,buffer));
            }
        } catch (BindException e) {
            System.err.println("Порт занят.");
        }

    }

    private static void readPort(){
        try {
            System.out.print("Введите порт для подключения: ");
            PORT = Integer.parseInt(new Scanner(System.in).next());
            if (PORT < 0 || PORT > 65535) throw new Exception();
        }catch (Exception e){
            System.out.println("Ошибка чтения порта, используется значение по умолчанию - 1025.");
            PORT = 1025;
        }
    }

    private static void addShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Thread.sleep(200);
                System.out.println("Выключение сервера ...");
                ServerLogger.logger.info("Выключение сервера");
 //               manager.save();
                for (Connector connector : users.values()) {
//                    connector.handler.manager.exit();
                    connector.send("--------------------------------\n---Сервер временно недоступен---\n--------------------------------");
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                ServerLogger.logger.error("",e);
            }finally {
                executor.shutdown();
            }
        }));
    }

    static class Reciever implements Runnable{
        static HashMap<InetSocketAddress,Connector> users = new HashMap<>();
        DatagramPacket packet;
        DatagramSocket datagramSocket;
        byte[] buffer;

        public Reciever(DatagramPacket packet, DatagramSocket datagramSocket, byte[] buffer) {
            this.packet = packet;
            this.datagramSocket = datagramSocket;
            this.buffer = buffer;
        }

        @Override
        public void run() {
            if (!users.containsKey(new InetSocketAddress(packet.getAddress(),packet.getPort()))) {
                System.out.println("Соединение с пользователем " + packet.getAddress() + ":" + packet.getPort() + " установлено.");
                ServerLogger.logger.info("Соединение с пользователем " + packet.getAddress() + ":" + packet.getPort() + " установлено.");
                InetSocketAddress address = new InetSocketAddress(packet.getAddress(),packet.getPort());
                Connector connector = new Connector(address,datagramSocket,buffer, databaseHandler);
                users.put(address,connector);
            }

            users.get(new InetSocketAddress(packet.getAddress(),packet.getPort())).receive(buffer);
        }
    }
}


