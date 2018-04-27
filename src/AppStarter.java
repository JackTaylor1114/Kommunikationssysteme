public class AppStarter {

    private static Client client;
    private static Server server;
    private static Worker worker;

    /* Main Method, starts Client, Server or Worker based on parameter input */
    public static void main(String[] args) {
        switch(args[0]){
            case "client":
                client = new Client();
                client.run();
                break;
            case "server":
                //Start server
                break;
            case "worker":
                //Start worker
                break;
        }
    }
}
