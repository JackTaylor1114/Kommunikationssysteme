import org.zeromq.ZMQ;

public class Server {

    public void run() {
        System.out.println("Server is starting");
        ZMQ.Context context;
        ZMQ.Socket socket;
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.ROUTER);
        socket.bind("tcp://*:5555");

        while (!Thread.currentThread().isInterrupted()) {
            byte[] recvData;
            while (true) {
                recvData = socket.recv();
                System.out.println(new String(recvData));
                switch (new String(recvData)){
                    case "Client":
                        System.out.println("Client connected successfully");
                        break;
                    case "Matrix":
                        //process matrix

                    default:

                }
            }
        }
        socket.close();
        context.term();
    }
}
