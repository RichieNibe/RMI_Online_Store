import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StoreServer {
    public static void main(String[] args) {
        try {
            StoreImpl store = new StoreImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Store", store);
            System.out.println("Server started...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

