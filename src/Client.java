import org.apache.commons.lang.SerializationUtils;
import org.zeromq.ZMQ;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* Client class for basic client options */
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

        //Setup
        System.out.println("Client is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.DEALER);
        socket.setIdentity(id.getBytes());
        socket.connect(protocol + ip + port);

        //Connect to the server
        System.out.println("Client connecting to server " + protocol + ip + port);
        socket.send("Client",ZMQ.SNDMORE);
        socket.send(socket.getIdentity(), 0);

        //Get feedback from the server
        byte[] message = socket.recv();
        System.out.println(new String (message));

        //Wait for console input and then send matrices to server
        System.out.println("Enter any input to send matrix to the server!");
        try {
            System.in.read();
        }
        catch(IOException e){
            System.out.println("Something went wrong when reading from console, please restart the client.");
            socket.close();
            context.term();
            System.exit(0);
        }
        Matrix matrix1 = buildMatrix1();
        Matrix matrix2 = buildMatrix2();
        List<Task> tasks = new ArrayList<>(){};
        for (int rowi = 0; rowi < matrix1.getRows(); rowi++) {
            for (int coli = 0; coli < matrix2.getCols(); coli++) {
                tasks.add(new Task(matrix1.getRow(rowi),matrix2.getCol(coli),rowi,coli,id));
            }
        }
        for(Task task: tasks) {
            socket.send("newTask", ZMQ.SNDMORE);
            socket.send(SerializationUtils.serialize(task),0);
            System.out.println("Task sent!");
        }

        /* Create a matrix to store result of the multiplication */
        Matrix result = new Matrix(matrix1.getRows(),matrix2.getCols());

        /* Wait for matrix to be filled */
        while(result.isSomethingNull()){
            Result resultObj  = (Result) SerializationUtils.deserialize(socket.recv());
            System.out.println("Received Result");
            result.setData(resultObj.getRow(),resultObj.getCol(),resultObj.getResult());
        }

        /* Finish and terminate client */
        System.out.println("Multiplication finished.");
        System.out.println("Result:");
        result.print();
        socket.close();
        context.term();

    }

    /* Generate a simple matrix */
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

    /* Generate a simple matrix */
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