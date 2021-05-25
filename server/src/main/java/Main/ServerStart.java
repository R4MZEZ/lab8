package Main;

import tools.ServerLogger;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Scanner;

public class ServerStart {
    static int PORT;
    static HashMap<InetSocketAddress,Connector> users = new HashMap<>();
    static CollectionManager manager;
    static DatabaseHandler databaseHandler;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String jdbcURL = "jdbc:postgresql://localhost:3125/studs";
        databaseHandler = new DatabaseHandler(jdbcURL, "s312515", "mej858");
        databaseHandler.connectToDatabase();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Thread.sleep(200);
                System.out.println("Выключение сервера ...");
                ServerLogger.logger.info("Выключение сервера");
                manager.save(manager);
                for (Connector connector : users.values()) {
//                    connector.handler.manager.exit();
                    connector.send("--------------------------------\n---Сервер временно недоступен---\n--------------------------------");
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                ServerLogger.logger.error("",e);
            }
        }));

        byte[] buffer;

        fillCollection();

        try {
            System.out.print("Введите порт для подключения: ");
            PORT = Integer.parseInt(new Scanner(System.in).next());
            if (PORT < 0 || PORT > 65535) throw new Exception();
        }catch (Exception e){
            System.out.println("Ошибка чтения порта, используется значение по умолчанию - 1025.");
            PORT = 1025;
        } //Чтение порта

        try {
            DatagramSocket datagramSocket = new DatagramSocket(PORT);
            ServerLogger.logger.info("Запуск сервера на порту {}", PORT);
            System.out.println("Ожидание подключения...");

            while (true) {
                buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                if (!users.containsKey(new InetSocketAddress(packet.getAddress(),packet.getPort()))) {
                    System.out.println("Соединение с пользователем " + packet.getAddress() + ":" + packet.getPort() + " установлено.");
                    ServerLogger.logger.info("Соединение с пользователем " + packet.getAddress() + ":" + packet.getPort() + " установлено.");
                    InetSocketAddress address = new InetSocketAddress(packet.getAddress(),packet.getPort());
                    Connector connector = new Connector(address,datagramSocket,buffer, databaseHandler);
                    users.put(address,connector);
                }

                users.get(new InetSocketAddress(packet.getAddress(),packet.getPort())).receive(buffer);
            }
        } catch (BindException e) {
            System.out.println("Порт занят.");
        }

    }

    /**
     *
     */
    private static void fillCollection(){
        manager = new CollectionManager();
        manager.setDatabaseHandler(databaseHandler);
        //TODO
    }
}


