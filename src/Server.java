import org.apache.commons.lang.SerializationUtils;
import org.zeromq.ZMQ;

import java.util.Queue;

class Server {

    @SuppressWarnings("all")
    private final String ip = "localhost";
    @SuppressWarnings("all")
    private final String port = ":5555";
    @SuppressWarnings("all")
    private final String protocol = "tcp://";

    //Attributes
    private Queue<TaskDTO> tasks;
    private int taskCounter = 0;

    /* Server RUN Method */
    void run() {
        System.out.println("Server is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.ROUTER);
        System.out.println("Server is binding address " + protocol + ip + port);
        socket.bind(protocol + ip + port);

        //Listen for messages
        while (!Thread.currentThread().isInterrupted()) {
            byte[] message = socket.recv();
            switch (new String(message)) {

                //React when Client is connected
                case "Client":
                    System.out.println("Client connected");

                    message = socket.recv();
                    System.out.println("Client ID: " + new String(message));

                    socket.send("client", ZMQ.SNDMORE);
                    socket.send("Server answers, everything is setup", 0);
                    break;

                //Receive Matrices from Client
                case "newMatrix":
                    System.out.println("Receiving matrices");

                    message = socket.recv();
                    Matrix matrix1  = (Matrix) SerializationUtils.deserialize(message);

                    message = socket.recv();
                    Matrix matrix2  = (Matrix) SerializationUtils.deserialize(message);

                    //Generate single tasks from the matrices and save them in a queue
                    int i = 0;
                    while(i<matrix1.getDimension()) {
                        tasks.add(new TaskDTO(matrix2.getRows().get(i), matrix1.getColumns().get(i), taskCounter ));
                        i++;
                    }

                    //Increase the taskCounter
                    taskCounter = taskCounter++;

                    break;

                case "newMWorker":
                    //React to worker connecting

                case "Result:":
                    //Handle result from Task

                default:
                    System.out.println("Received message");
                    System.out.println("Unable to resolve sender or purpose");
            }

            //Look for tasks to send
        }
        socket.close();
        context.term();
    }
}