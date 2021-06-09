package Client;


import tools.ClientLogger;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.DatagramChannel;


public class Connector{
    InetSocketAddress serverAddress;
    DatagramChannel client;

    ByteArrayOutputStream b1 = new ByteArrayOutputStream(1024);
    ObjectOutputStream outputStream;
    ObjectInput input;
    protected static boolean isExit = false;
    String relevantMessage;

    ByteBuffer byteBuffer;
    byte[] buffer = new byte[1024];

    public Connector(int PORT) {
        try {
            serverAddress = new InetSocketAddress("localhost", PORT);
            client = DatagramChannel.open();
            client.bind(null);
            client.configureBlocking(false);
            outputStream = new ObjectOutputStream(b1);
        } catch (IOException e) {
            ClientLogger.logger.error("Ошибка в конструкторе Коннектора", e);
        }
    }


    public void send(Object data) {
        try {
            b1 = new ByteArrayOutputStream(1024);
            outputStream = new ObjectOutputStream(b1);
            outputStream.writeObject(data);

            byteBuffer = ByteBuffer.wrap(b1.toByteArray());
            client.send(byteBuffer, serverAddress);

        } catch (IOException e) {
            ClientLogger.logger.error("Ошибка при отправке данных", e);
        }
    }

    public String receive() {
        try {
            Thread.sleep(100);
            buffer = new byte[2048];
            client.receive(ByteBuffer.wrap(buffer));
            Main.connected = true;
            input = new ObjectInputStream(new ByteArrayInputStream(buffer));
            relevantMessage = (String) input.readObject();
            if (relevantMessage.startsWith("Добро пожаловать")
                    || relevantMessage.startsWith("С возвращением, ")) Commander.isAuth = true;
            return relevantMessage;

        }catch (ClosedByInterruptException | InterruptedException ignored){
            return "";
        }
        catch (IOException | ClassNotFoundException e) {
            ClientLogger.logger.error("Ошибка при принятии данных", e);
            return "Ошибка";
        }

    }

}


