import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StoreClient {
    private StoreClient() {}

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(null);
            StoreInterface stub = (StoreInterface) registry.lookup("Store");
            String response = stub.registerUser("User", "1234");
            String item = stub.addItem(new Item("1", " Corn", 20.0, "Yellow corn", 2));
            String remove = stub.removeItem(Item.getId());
            System.out.println(remove);
            System.out.println("Response: " + response);
            System.out.println(item);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
