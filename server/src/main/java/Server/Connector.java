package Server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connector implements Runnable{
    protected final InetAddress userAddress;
    protected final int userPort;

    ByteArrayOutputStream b1 = new ByteArrayOutputStream(1024);
    ObjectOutputStream outputStream;

    byte[] buffer;
    ObjectInputStream input;

    DatagramSocket datagramSocket;

    Handler handler;

    public Connector(InetAddress userAddress, int userPort, byte[] buffer){
        this.userAddress = userAddress;
        this.userPort = userPort;
        this.buffer = buffer;
        try {
            datagramSocket = new DatagramSocket(ServerStart.PORT-1);
            outputStream = new ObjectOutputStream(b1);
            input = new ObjectInputStream(new ByteArrayInputStream(this.buffer));
            handler = new Handler((String) input.readObject(), this);
        }catch (IOException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        Thread thread = new Thread(handler);
        thread.start();
        try {
            thread.join();
        }catch (InterruptedException ignored){}
    }

    public void send(Object data){
        try {
            b1 = new ByteArrayOutputStream(1024);
            outputStream = new ObjectOutputStream(b1);
            outputStream.writeObject(data);
            datagramSocket.send(new DatagramPacket(b1.toByteArray(), b1.toByteArray().length, userAddress, userPort));
        }catch (IOException ignored){}
    }

    public Object receive(){
        try {
            buffer = new byte[1024];
            datagramSocket.receive(new DatagramPacket(buffer,buffer.length));
            input = new ObjectInputStream(new ByteArrayInputStream(buffer));
            return input.readObject();

        }catch (IOException | ClassNotFoundException ignored){return null;}

    }
}
