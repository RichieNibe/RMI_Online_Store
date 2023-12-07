import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws RemoteException {
        if (args.length > 0 && args[0].equals("server")) {
            StoreServer.main(args);
        } else if (args.length > 0 && args[0].equals("client")) {
            StoreClient.main(args);
        } else {
            System.out.println("Usage: java Main <server/client> [other args]");
        }
    }
}