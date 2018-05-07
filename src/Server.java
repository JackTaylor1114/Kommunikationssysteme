import org.apache.commons.lang.SerializationUtils;
import org.zeromq.ZMQ;

class Server {

    @SuppressWarnings("all")
    private final String ip = "localhost";
    @SuppressWarnings("all")
    private final String port = ":5555";
    @SuppressWarnings("all")
    private final String protocol = "tcp://";

    private Matrix matrix1;
    private Matrix matrix2;

    void run() {
        System.out.println("Server is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.ROUTER);
        System.out.println("Server is binding address " + protocol + ip + port);
        socket.bind(protocol + ip + port);

        while (!Thread.currentThread().isInterrupted()) {
            byte[] message = socket.recv();

            switch (new String(message)) {
                case "Client":
                    System.out.println("Client connected");

                    message = socket.recv();

                    System.out.println("ID: " + new String(message));

                    message = socket.recv();

                    System.out.println("Data: " + new String(message));

                    socket.send("client", ZMQ.SNDMORE);
                    socket.send("Answer from the Server", 0);

                    break;

                case "newMatrix":
                    System.out.println("Receiving Matrix");

                    message = socket.recv();
                    matrix1  = (Matrix) SerializationUtils.deserialize(message);
                    System.out.println(matrix1.getRows().get(0).getValues()[0]);

                    message = socket.recv();
                    matrix2  = (Matrix) SerializationUtils.deserialize(message);
                    break;
            }
        }
        socket.close();
        context.term();
    }
}
