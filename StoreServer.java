import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StoreServer {
    public static void main(String[] args) {
        try {
            StoreInterface stub = new StoreImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Store", stub);
            System.out.println("Server started...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

