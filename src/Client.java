import org.apache.commons.lang.SerializationUtils;
import org.zeromq.ZMQ;

import java.io.IOException;

class Client {

    @SuppressWarnings("all")
    private final String ip = "localhost";
    @SuppressWarnings("all")
    private final String port = ":5555";
    @SuppressWarnings("all")
    private final String protocol = "tcp://";
    @SuppressWarnings("all")
    private final String id = "client";

    /* Client RUN method */
    void run() {
        System.out.println("Client is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.DEALER);
        socket.setIdentity(id.getBytes());
        socket.connect(protocol + ip + port);

        //Connect to the server
        System.out.println("Client connecting to server " + protocol + ip + port);
        socket.send("Client",ZMQ.SNDMORE);
        socket.send(socket.getIdentity(), 0);

        //Get feedback by the server
        byte[] message = socket.recv();
        System.out.println(new String (message));

        //Wait for console input and then send matrices to server
        System.out.println("Press any KEY to send matrix to the server!");
        try {
            System.in.read();
        }
        catch(IOException e){
            System.out.println("Something went wrong when reading from console, please restart the client.");
            socket.close();
            context.term();
            System.exit(0);
        }
        socket.send("newMatrix", ZMQ.SNDMORE);
        socket.send(id, ZMQ.SNDMORE);
        socket.send(SerializationUtils.serialize(buildMatrix1()),ZMQ.SNDMORE);
        socket.send(SerializationUtils.serialize(buildMatrix2()),0);
        socket.close();
        context.term();

    }

    /* Generate a simple Matrix */
    private Matrix buildMatrix1(){
        Matrix matrix1 = new Matrix(3,2);
        matrix1.setData(0,0,3);
        matrix1.setData(0,1,2);
        matrix1.setData(0,2,1);

        matrix1.setData(1,0,1);
        matrix1.setData(1,1,0);
        matrix1.setData(1,2,2);
        return matrix1;
    }

    /* Generate a simple Matrix */
    private Matrix buildMatrix2(){
        Matrix matrix2 = new Matrix(2,3);
        matrix2.setData(0,0,1);
        matrix2.setData(0,1,2);

        matrix2.setData(1,0,0);
        matrix2.setData(1,1,1);

        matrix2.setData(2,0,4);
        matrix2.setData(2,1,0);
        return matrix2;
    }
}