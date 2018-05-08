import org.apache.commons.lang.SerializationUtils;
import org.zeromq.ZMQ;

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
        System.out.println(new String(socket.recv()));

        //Listen for messages with tasks
        while (!Thread.currentThread().isInterrupted()) {
            Task task  = (Task) SerializationUtils.deserialize(socket.recv());
            if(task != null) {
                task = calculate(task);
                task.sign(this.id);
                socket.send("Result", ZMQ.SNDMORE);
                socket.send(SerializationUtils.serialize(task), 0);
            }
        }
        socket.close();
        context.term();
    }

    /* Calculate result from row and column and return the task */
    private Task calculate(Task task) {
        int i = 0;
        int result = 0;
        while(task.getDimension()<i) {
            result = result + (task.getRow().getValues()[i] * task.getColumn().getValues()[i]);
            i++;
        }
        task.setResult(result);
        return task;
    }
}