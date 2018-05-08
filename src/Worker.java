import org.apache.commons.lang.SerializationUtils;
import org.zeromq.ZMQ;
import java.io.IOException;

class Worker {

    @SuppressWarnings("all")
    private final String ip = "localhost";
    @SuppressWarnings("all")
    private final String port = ":5555";
    @SuppressWarnings("all")
    private final String protocol = "tcp://";
    @SuppressWarnings("all")
    private String id = "";

    /* Worker RUN method */
    void run(String id) {
        System.out.println(id+" is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.DEALER);
        this.id = "worker"+id;
        socket.setIdentity(id.getBytes());
        socket.connect(protocol + ip + port);

        //Connect to the server
        System.out.println(id+" connecting to server " + protocol + ip + port);
        socket.send("Worker", ZMQ.SNDMORE);
        socket.send(socket.getIdentity(), 0);

        //Get feedback by the server
        byte[] message = socket.recv();
        System.out.println(new String(message));

        //Wait for Task

        socket.close();
        context.term();
    }
}