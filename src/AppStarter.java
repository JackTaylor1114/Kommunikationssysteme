public class AppStarter {

    private Client client;
    private Server server;
    private Worker worker;

    public static void main(String[] args) {
        switch(args[0]){
            case "client":
                //Start Client
                break;
            case "server":
                //Start server
                break;
            case "worker":
                //Start worker
                break;
        }
        System.out.println("Test Test");
    }
}
