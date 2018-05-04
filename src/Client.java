import org.zeromq.ZMQ;

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

        socket.close();
        context.term();

        //Look for 2 CSV with 2 matrix
        //convert csv to rows and columns and create a matrix with them
        //send matrix to server
        //wait for response from server
    }
}