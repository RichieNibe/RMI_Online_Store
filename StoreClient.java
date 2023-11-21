import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StoreClient {
    private StoreClient() {}

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(null);
            StoreInterface stub = (StoreInterface) registry.lookup("Store");
            String response = stub.registerUser("User", "1234");
            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
