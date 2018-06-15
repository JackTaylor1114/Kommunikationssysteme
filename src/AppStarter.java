/* Starting class for the matrix calculation system */
class AppStarter {

    /* Main Method, starts Client, Server or Worker (1 or 2) based on parameter input */
    public static void main(String[] args) {
        switch(args[0]){
            case "client":
                Client client = new Client();
                client.run();
                break;
            case "server":
                Server server = new Server();
                server.run();
                break;
            case "worker1":
                Worker worker = new Worker();
                worker.run("1");
                break;
            case "worker2":
                Worker worker2 = new Worker();
                worker2.run("2");
                break;
        }
    }
}