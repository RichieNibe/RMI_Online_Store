import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class StoreClient {
    private StoreClient() {}

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            StoreInterface stub = (StoreInterface) registry.lookup("Store");
            String response = stub.registerUser("User", "1234", true);
            User user = stub.
            String i = stub.addItem(new Item("5", " Corn", 20.0, "Yellow corn", 2));


            System.out.println("Response: " + response);
            System.out.println(item);


        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
