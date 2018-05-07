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

    void run() {
        System.out.println("Client is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.DEALER);
        socket.setIdentity(id.getBytes());
        socket.connect(protocol + ip + port);
        System.out.println("Client connecting to Server " + protocol + ip + port);
        socket.send("Client",ZMQ.SNDMORE);
        socket.send(socket.getIdentity(), ZMQ.SNDMORE);
        socket.send("Data, dude trust me", 0);

        byte[] message = socket.recv();
        System.out.println(new String (message));
        System.out.println("Press ENTER to send Matrix");
        try {
            System.in.read();
        }
        catch(IOException e){
            //lol
        }
        socket.send("newMatrix", ZMQ.SNDMORE);
        socket.send(SerializationUtils.serialize(buildMatrix1()),ZMQ.SNDMORE);
        socket.send(SerializationUtils.serialize(buildMatrix2()),0);
        socket.close();
        context.term();

        //Look for 2 CSV with 2 matrix
        //convert csv to rows and columns and create a matrix with them
        //send matrix to server
        //wait for response from server
    }

    private Matrix buildMatrix1(){
        Matrix matrix1 = new Matrix();
        int[] array = {3,2,1};
        MatrixRow row1 = new MatrixRow(array,1);
        int[] array2={1,0,2};
        MatrixRow row2 = new MatrixRow(array2, 2);
        matrix1.addRow(row1);
        matrix1.addRow(row2);
        return matrix1;
    }

    private Matrix buildMatrix2(){
        Matrix matrix2 = new Matrix();
        int[] array = {1,0,4};
        MatrixColumn col1 = new MatrixColumn(array,1);
        int[] array2={2,1,0};
        MatrixColumn col2 = new MatrixColumn(array2, 2);
        matrix2.addColumn(col1);
        matrix2.addColumn(col2);
        return matrix2;
    }

}