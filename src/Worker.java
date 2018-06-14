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
        this.id = "worker"+id;
        System.out.println(this.id+" is starting");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.DEALER);
        socket.setIdentity(this.id.getBytes());
        socket.connect(protocol + ip + port);

        //Connect to the server
        System.out.println(this.id+" connecting to server " + protocol + ip + port);
        socket.send("Worker", ZMQ.SNDMORE);
        socket.send(socket.getIdentity(), 0);
        System.out.println(new String(socket.recv()));

        //Listen for messages with tasks
        while (!Thread.currentThread().isInterrupted()) {
            Task task  = (Task) SerializationUtils.deserialize(socket.recv());
            if(task != null) {
                System.out.println("Received Task. Calculating...");
                Result result = calculate(task);
                result.setWorkerID(this.id);
                socket.send("Result", ZMQ.SNDMORE);
                socket.send(SerializationUtils.serialize(result), 0);
            }
        }
        socket.close();
        context.term();
    }

    /* Calculate result from row and column and return the task */
    private Result calculate(Task task) {
        Result result = new Result(task.getColumn(),task.getRow(), task.getClientID());
        int solution = 0;
        for (int i = 0; i < task.getCalcLength(); i++) {
            solution = solution + (task.getRowData()[i] * task.getColumnData()[i]);
        }
        result.setResult(solution);
        return result;
    }
}