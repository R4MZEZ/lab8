package Client;

import Commands.CommandReady;
import tools.ClientLogger;

import java.util.Scanner;

public class Main {
    static boolean connected = false;

    public static void main(String[] args) throws InterruptedException {
        Commander commander;
        CommandReady commandReady = new CommandReady();
        int count = 0;
        int PORT;
        try {
            System.out.print("Введите порт для подключения: ");
            PORT = Integer.parseInt(new Scanner(System.in).next());
            if (PORT < 0 || PORT >65535) throw new Exception();
        }catch (Exception e){
            System.out.println("Ошибка чтения порта, используется значение по умолчанию.");
            PORT = 1216;
        }

        ClientLogger.logger.info("Попытка подключения к серверу с портом {}",PORT);
        commander = new Commander(commandReady,PORT);
        System.out.println("Ожидание ответа сервера(10 секунд)...");
        while (!connected & count++<10) {
            Thread.sleep(1000);
        }
        if (count != 11) {
            ClientLogger.logger.info("Подключено к серверу с портом {}", PORT);
            commander.interactiveMod(System.in);
            Connector.isExit = true;
            Commander.connectorThread.interrupt();
        }
        else {
            ClientLogger.logger.error("Неудачная попытка соединения: сервер с портом {} не отвечает", PORT);
            Connector.isExit = true;
            Commander.connectorThread.interrupt();
        }

    }

}