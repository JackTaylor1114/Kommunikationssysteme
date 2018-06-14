import org.apache.commons.lang.SerializationUtils;
import org.zeromq.ZMQ;

import java.util.LinkedList;
import java.util.Queue;

class Server {

    @SuppressWarnings("all")
    private final String ip = "localhost";
    @SuppressWarnings("all")
    private final String port = ":5555";
    @SuppressWarnings("all")
    private final String protocol = "tcp://";

    //Attributes
    private Queue<Task> tasks = new LinkedList<>(){};
    private Queue<String> workers = new LinkedList<>();
    private int taskCounter = 0;
    private ZMQ.Socket socket;

    /* Server RUN Method */
    void run() {
        System.out.println("Server is starting");
        ZMQ.Context context = ZMQ.context(1);
        socket = context.socket(ZMQ.ROUTER);
        System.out.println("Server is binding address " + protocol + ip + port);
        socket.bind(protocol + ip + port);

        //Listen for messages
        while (!Thread.currentThread().isInterrupted()) {
            byte[] message = socket.recv();
            switch (new String(message)) {

                //React when client is connected
                case "Client":
                    //Acknowledge connection to client
                    System.out.println("Client connected");
                    message = socket.recv();
                    System.out.println("Client ID: " + new String(message));
                    socket.send("client", ZMQ.SNDMORE);
                    socket.send("Server answers, everything is setup", 0);
                    break;

                //React when worker is connecting
                case "Worker":
                    //Acknowledge connection to worker and save its ID
                    System.out.println("Worker connected");
                    message = socket.recv();
                    String workerID = new String(message);
                    System.out.println("Worker ID: " + workerID);
                    workers.add(workerID);
                    socket.send(workerID, ZMQ.SNDMORE);
                    socket.send("Server answers, everything is setup", 0);
                    break;

                //React when client sends matrices
                case "newTask":
                    System.out.println("Receiving Task");
                    //Convert message to task, adding Task to taskList
                    message = socket.recv();
                    tasks.add((Task) SerializationUtils.deserialize(message));
                    break;

                case "Result":
                    System.out.println("Receiving result!");
                    message = socket.recv();
                    Result result = ((Result) SerializationUtils.deserialize(message));
                    workers.add(result.getWorkerID());
                    System.out.println("Sending result to client");
                    socket.send(result.getClientID(), ZMQ.SNDMORE);
                    socket.send(SerializationUtils.serialize(result), 0);
                    break;

                    //TODO: Handle result (add worker to list, send result to client)
            }

            //If queued task exist, forward one to a worker
            if(tasks.size() > 0) {
                forwardTask();
            }
        }
        socket.close();
        context.term();
    }

    /* Forward the oldest task to an worker */
    private void forwardTask() {
        System.out.println("Attempting to forward a task to a worker");
        String workerID = workers.poll();
        if (workerID == null) {
            System.out.println("No available workers found!");
        }
        else {
            System.out.println("Sending task to worker with ID: "+workerID);
            socket.send(workerID, ZMQ.SNDMORE);
            socket.send(SerializationUtils.serialize(tasks.poll()), 0);
        }
    }
}