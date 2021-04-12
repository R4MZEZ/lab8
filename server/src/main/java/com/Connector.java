package com;

import Commands.Command;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Connector{
    protected final InetSocketAddress userSocketAddress;

    Command command;
    Thread thread;

    ByteArrayOutputStream b1 = new ByteArrayOutputStream(1024);
    ObjectOutputStream outputStream;

    byte[] buffer;
    ObjectInputStream input;

    DatagramSocket datagramSocket;

    Handler handler;

    public Connector(InetSocketAddress address, DatagramSocket socket, byte[] buffer) {
        handler = new Handler(this);
        thread = new Thread(handler);
        handler.setThread(thread);
//        thread.setDaemon(true);
        thread.start();

        this.buffer = buffer;
        this.userSocketAddress = address;
        this.datagramSocket = socket;
        try {
            outputStream = new ObjectOutputStream(b1);
            input = new ObjectInputStream(new ByteArrayInputStream(this.buffer));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

//    @Override
//    public void run() {
//
//        thread = new Thread(handler);
//        thread.start();
////        try {
////            thread.join();
////        }catch (InterruptedException ignored){}
//    }

    public void send(Object data){
        try {
            System.out.println("Sending");
            b1 = new ByteArrayOutputStream(1024);
            outputStream = new ObjectOutputStream(b1);
            outputStream.writeObject(data);
            datagramSocket.send(new DatagramPacket(b1.toByteArray(), b1.toByteArray().length, userSocketAddress));
        }catch (IOException ignored){}
    }

    public synchronized void receive(byte[] buffer){
        try {
            this.buffer = buffer;
//            datagramSocket.receive(new DatagramPacket(buffer,buffer.length));
            input = new ObjectInputStream(new ByteArrayInputStream(buffer));
            command = (Command) input.readObject();
            synchronized (thread) {
                System.out.println("Будим хэндлер" + thread.toString());
//                while(thread.getState() != Thread.State.WAITING) thread.notifyAll();
                if (thread.getState() != Thread.State.WAITING){
                    thread.interrupt();
                    handler = new Handler(this);
                    thread = new Thread(handler);
                    handler.setThread(thread);
                    thread.start();
                    System.out.println("ВСЕ В ГОВНЕ");
                    synchronized (thread){thread.notify();}
                    return;
                }
                thread.notify();
                System.out.println();
            }
        } catch (IOException | ClassNotFoundException | IllegalMonitorStateException e) {
            e.printStackTrace();
        }

    }
}
