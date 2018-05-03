import org.zeromq.ZMQ;

class Client {

    @SuppressWarnings("all")
    private final String ip = "localhost";
    @SuppressWarnings("all")
    private final String port = ":5555";
    @SuppressWarnings("all")
    private final String protocol = "tcp://";

    void run() {
        System.out.println("Client is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REQ);
        socket.connect(protocol + ip + port);
        System.out.println("Client connecting to Server " + protocol + ip + port);
        socket.send("Client",ZMQ.REP);
        System.out.println(new String(socket.recv()));
        socket.close();
        context.term();

        //Look for 2 CSV with 2 matrix
        //convert csv to rows and columns and create a matrix with them
        //send matrix to server
        //wait for response from server
    }
}