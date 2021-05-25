package Main;

import com.sun.xml.bind.v2.TODO;
import content.Flat;
import content.House;
import tools.ServerLogger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class ServerStart {
    static int PORT;
    static HashMap<InetSocketAddress,Connector> users = new HashMap<>();
    static String filepath;
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

        if (args.length > 0) filepath = args[0];
        else filepath = "C:\\Users\\User\\Desktop\\lab6\\client\\src\\main\\java\\inputData\\input.xml";
        fillCollection(filepath);

        try {
            System.out.print("Введите порт для подключения: ");
            PORT = Integer.parseInt(new Scanner(System.in).next());
            if (PORT < 0 || PORT >65535) throw new Exception();
        }catch (Exception e){
            System.out.println("Ошибка чтения порта, используется значение по умолчанию - 1025.");
            PORT = 1025;
        } //Чтение порта

        try {
            DatagramSocket datagramSocket = new DatagramSocket(PORT);
            ServerLogger.logger.info("Запуск сервера на порту {}", PORT);
            System.out.println("Ожидание подключения...");

            for (Connector connector : users.values()) {
                connector.send("--------------------------------\n---Сервер снова доступен---\n--------------------------------");
            }

            while (true) {
                buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                if (!users.containsKey(new InetSocketAddress(packet.getAddress(),packet.getPort()))) {
                    System.out.println("Соединение с пользователем " + packet.getAddress() + ":" + packet.getPort() + " установлено.");
                    ServerLogger.logger.info("Соединение с пользователем " + packet.getAddress() + ":" + packet.getPort() + " установлено.");
                    InetSocketAddress address = new InetSocketAddress(packet.getAddress(),packet.getPort());
                    Connector connector = new Connector(address,datagramSocket,buffer);
                    users.put(address,connector);
                }

                users.get(new InetSocketAddress(packet.getAddress(),packet.getPort())).receive(buffer);
            }
        } catch (BindException e) {
            System.out.println("Порт занят.");
        }

    }

    /**
     * Заполнить коллекцию из файла
     * @param filePath путь до файла
     */
    private static void fillCollection(String filePath){
        manager = new CollectionManager();
        manager.setDatabaseHandler(databaseHandler);
        //TODO
//        JAXBContext context;
//        Unmarshaller unmarshaller;
//        BufferedInputStream stream;
//        try {
//            manager = new CollectionManager();
//            context = JAXBContext.newInstance(Flat.class, CollectionManager.class, House.class);
//            unmarshaller = context.createUnmarshaller();
//            stream = new BufferedInputStream(new FileInputStream(filePath));
//            manager = (CollectionManager) unmarshaller.unmarshal(stream);
//            Iterator<Flat> iterator = manager.getFlats().listIterator();
//            while (iterator.hasNext()) {
//                if (iterator.next().isEmpty()) {
//                    System.out.println("Ошибка! Одна из квартир не была добавлена в коллекцию, т.к. одно или несколько полей не были указаны, либо выходят за допустимый диапазон.");
//                    iterator.remove();
//                }
//            }
//        } catch (NumberFormatException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            System.out.println("Ошибка! Невозможно считать коллекцию из файла, т.к. одно или несколько полей указаны в некорректном формате (например, на месте числа - строка).");
//            ServerLogger.logger.error("Ошибка! Невозможно считать коллекцию из файла, т.к. одно или несколько полей указаны в некорректном формате (например, на месте числа - строка).");
//
//        } catch (FileNotFoundException e) {
//            System.out.println("Ошибка! Файл с входными данными не найден, проверьте путь и права доступа к файлу.");
//            ServerLogger.logger.error("Ошибка! Файл с входными данными не найден, проверьте путь и права доступа к файлу.");
//
//        } catch (JAXBException e) {
//            System.out.println("Ошибка при десериализации документа. Проверьте правильность разметки.");
//            ServerLogger.logger.error("Ошибка при десериализации документа. Проверьте правильность разметки.");
//
//        }
    }
}


