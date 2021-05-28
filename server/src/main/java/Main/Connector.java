package Main;

import Commands.Command;
import tools.ServerLogger;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Connector{

    protected final InetSocketAddress userSocketAddress; //Сокет-адрес подключенного пользователя
    static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    Command command; //Актуальная команда для выполнения Handler'ом
    Thread thread; //Поток Handler'a
    Handler handler;

    ByteArrayOutputStream b1 = new ByteArrayOutputStream(1024); //Потоки для отправки ответов
    ObjectOutputStream outputStream;
    ObjectInputStream input;
    byte[] buffer;

    DatabaseHandler databaseHandler;

    DatagramSocket datagramSocket; //Сокет для отправки ответов

    public Connector(InetSocketAddress address, DatagramSocket socket, byte[] buffer, DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        handler = new Handler(this);
        thread = new Thread(handler);
        handler.setThread(thread);
        thread.start();

        this.buffer = buffer;
        this.userSocketAddress = address;
        this.datagramSocket = socket;
        try {
            outputStream = new ObjectOutputStream(b1);
            input = new ObjectInputStream(new ByteArrayInputStream(this.buffer));
        } catch (IOException exception) {
            ServerLogger.logger.error("",exception);
        }

    }

    /**
     * Отправить пользователю данные
     * @param data - данные
     */
    public void send(Object data){
        executor.execute(new Sender(data));
    }

    /**
     * Принять данные от пользователя
     * @param buffer данные в сериализованном виде
     */
    public synchronized void receive(byte[] buffer){
        try {
            this.buffer = buffer;
            input = new ObjectInputStream(new ByteArrayInputStream(buffer));
            command = (Command) input.readObject();
            if (command.getClass().getName().equals("Commands.CommandExecuteScript")) CollectionManager.pathList.clear();
            synchronized (thread) {
                if (thread.getState() != Thread.State.WAITING){
                    thread.interrupt();
                    handler = new Handler(this);
                    thread = new Thread(handler);
                    handler.setThread(thread);
                    thread.start();
                    synchronized (thread){thread.notify();}
                    return;
                }
                thread.notify();
            }
        } catch (IOException | ClassNotFoundException | IllegalMonitorStateException e) {
            ServerLogger.logger.error("Ошибка при получении данных",e);
            e.printStackTrace();
        }

    }
    class Sender implements Runnable{
        Object data;

        public Sender(Object data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                b1 = new ByteArrayOutputStream(1024);
                outputStream = new ObjectOutputStream(b1);
                outputStream.writeObject(data);
                datagramSocket.send(new DatagramPacket(b1.toByteArray(), b1.toByteArray().length, userSocketAddress));
            }catch (IOException exception){
                ServerLogger.logger.error("",exception);
            }
        }
    }
}
