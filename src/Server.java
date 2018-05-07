import org.apache.commons.lang.SerializationUtils;
import org.zeromq.ZMQ;

class Server {

    @SuppressWarnings("all")
    private final String ip = "localhost";
    @SuppressWarnings("all")
    private final String port = ":5555";
    @SuppressWarnings("all")
    private final String protocol = "tcp://";

    //Attributes
    private Matrix matrix1;
    private Matrix matrix2;

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
                    System.out.println("Receiving Matrix");

                    message = socket.recv();
                    matrix1  = (Matrix) SerializationUtils.deserialize(message);

                    message = socket.recv();
                    matrix2  = (Matrix) SerializationUtils.deserialize(message);
                    break;

                case "newMWorker":
                    //React to worker connecting

                case "Result:":
                    //Handle result from Task

                default:
                    System.out.println("Received message");
                    System.out.println("Unable to resolve sender or purpose");
            }
        }
        socket.close();
        context.term();
    }
}