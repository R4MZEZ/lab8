package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerStart {
    static int PORT = 1216;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[1024];

        while(true) {
            DatagramSocket datagramSocket = new DatagramSocket(PORT++);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Ожидание подключения...");
            datagramSocket.receive(packet);
            System.out.println("Соединение с пользователем " + packet.getAddress() + ":" + packet.getPort() + " установлено.");
            datagramSocket.close();
            new Thread(new Connector(packet.getAddress(), packet.getPort(), buffer)).start();
        }
    }
}
