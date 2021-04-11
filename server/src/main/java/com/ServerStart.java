package com;

import content.Flat;
import content.House;

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

    public static void main(String[] args) throws IOException{
        byte[] buffer;

        if (args.length > 0) filepath = args[0];
        else filepath = "C:\\Users\\User\\Desktop\\lab6\\client\\src\\main\\java\\inputData\\input.xml";
        fillCollection(filepath);

        try {
            System.out.print("Введите порт для подключения: ");
            PORT = Integer.parseInt(new Scanner(System.in).next());
        }catch (Exception e){
            System.out.println("Ошибка чтения порта, используется значение по умолчанию - 1025.");
            PORT = 1025;
        }

        try {
            DatagramSocket datagramSocket = new DatagramSocket(PORT);
            System.out.println("Ожидание подключения...");

            while (true) {
                buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                if (!users.containsKey(new InetSocketAddress(packet.getAddress(),packet.getPort()))) {
                    System.out.println("Соединение с пользователем " + packet.getAddress() + ":" + packet.getPort() + " установлено.");
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

    private static void fillCollection(String filePath){
        JAXBContext context;
        Unmarshaller unmarshaller;
        BufferedInputStream stream;
        try {
            context = JAXBContext.newInstance(Flat.class, CollectionManager.class, House.class);
            unmarshaller = context.createUnmarshaller();
            stream = new BufferedInputStream(new FileInputStream(filePath));
            manager = (CollectionManager) unmarshaller.unmarshal(stream);
            Iterator<Flat> iterator = manager.getFlats().listIterator();
            while (iterator.hasNext()) {
                if (iterator.next().isEmpty()) {
                    System.out.println("Ошибка! Одна из квартир не была добавлена в коллекцию, т.к. одно или несколько полей не были указаны, либо выходят за допустимый диапазон.");
                    iterator.remove();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! Невозможно считать коллекцию из файла, т.к. одно или несколько полей указаны в некорректном формате (например, на месте числа - строка).");
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка! Файл с входными данными не найден, проверьте путь и права доступа к файлу.");
        } catch (JAXBException e) {
            System.out.println("Ошибка при десериализации документа. Проверьте правильность разметки.");
        }
    }
}


