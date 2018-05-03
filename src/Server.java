import org.zeromq.ZMQ;

class Server {

    @SuppressWarnings("all")
    private final String ip = "localhost";
    @SuppressWarnings("all")
    private final String port = ":5555";
    @SuppressWarnings("all")
    private final String protocol = "tcp://";

    void run() {
        System.out.println("Server is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        System.out.println("Server is binding address " + protocol + ip + port);
        socket.bind(protocol + ip + port);

        while (!Thread.currentThread().isInterrupted()) {
            byte[] message = socket.recv();

            switch (new String(message)) {
                case "Client":
                    System.out.println("Client connected");
                    socket.send("Server received connection by you!");
                    break;
            }
        }
        socket.close();
        context.term();
    }
}
