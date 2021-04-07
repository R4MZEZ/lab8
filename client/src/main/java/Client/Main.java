package Client;

import java.util.Scanner;

public class Main {
    static boolean connected = false;

    public static void main(String[] args) throws InterruptedException {
        Commander commander;
        String filepath = "null";
        int count = 0;
        int PORT;
        try {
            System.out.print("Введите порт для подключения: ");
            PORT = Integer.parseInt(new Scanner(System.in).next());
        }catch (Exception e){
            System.out.println("Ошибка чтения порта, используется значение по умолчанию.");
            PORT = 1216;
        }


        try {
            filepath = args[0];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка. Отсутствует аргумент входного файла.");
        }

        commander = new Commander(filepath,PORT);
        System.out.println("Ожидание ответа сервера(10 секунд)...");
        while (!connected & count++<10) {
            Thread.sleep(1000);
        }
        if (count != 11) commander.interactiveMod(System.in);
        else {
            Connector.isExit = true;
            Commander.connectorThread.interrupt();
        }

    }

}