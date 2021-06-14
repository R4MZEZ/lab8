package Client;


import javafx.scene.paint.Color;
import tools.ClientLogger;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;


public class Connector {
    InetSocketAddress serverAddress;
    DatagramChannel client;

    ByteArrayOutputStream b1 = new ByteArrayOutputStream(1024);
    ObjectOutputStream outputStream;
    ObjectInput input;
    protected static boolean isExit = false;

    ByteBuffer byteBuffer;
    byte[] buffer = new byte[1024];

    private int attemps = 0;

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


    public void send(Commands.Command data) {
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

    public <T> T receive() {
        try {
            Thread.sleep(100);
            buffer = new byte[2048];
            client.receive(ByteBuffer.wrap(buffer));
            Main.connected = true;
            input = new ObjectInputStream(new ByteArrayInputStream(buffer));
            Object answer = input.readObject();
            return (T) answer;

        } catch (StreamCorruptedException e) {
            if (attemps++ > 5){
                attemps = 0;
                return (T) "Server connection error. Please try again.";
            }
            return receive();
        } catch (ClosedByInterruptException | InterruptedException ignored) {
            return (T) "";
        } catch (IOException | ClassNotFoundException e) {
            ClientLogger.logger.error("Ошибка при принятии данных", e);
            e.printStackTrace();
            return (T) "Ошибка";
        }

    }

}


