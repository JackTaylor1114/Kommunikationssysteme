import org.zeromq.ZMQ;

public class Client {

    private final String ip = "localhost";
    private final String port = ":5555";
    private final String protocol = "tcp://";
    private final byte[] id = "Client".getBytes();

    protected void run() {
        System.out.println("Client is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.DEALER);
        socket.setIdentity(id);
        socket.connect(protocol + ip + port);
        System.out.println("Client connecting to Server");
        socket.send("Client", ZMQ.SNDMORE);

        //Look for 2 CSV with 2 matrix
        //convert csv to rows and columns and create a matrix with them
        //send matrix to server
        //wait for response from server
    }
}
