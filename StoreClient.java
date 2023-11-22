import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class StoreClient {
    private StoreInterface storeStub;
    private User currentUser;
    private ShoppingCart currentCart;

    public StoreClient() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            storeStub = (StoreInterface) registry.lookup("Store");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void registerUser(String username, String password, boolean isAdmin) {
        try {
            storeStub.registerUser(username, password, isAdmin);
            System.out.println("User registered successfully.");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
        }
    }

    public void loginUser(String username, String password) {
        try {
            User user = storeStub.loginUser(username, password);
            if (user != null) {
                currentUser = user;
                currentCart = new ShoppingCart();
                System.out.println("Logged in successfully.");
            } else {
                System.out.println("Login failed.");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
        }
    }

    public void browseItems() {
        try {
            List<Item> items = storeStub.browseItems();
            System.out.println("Available items:");
            for (Item item : items) {
                System.out.println(item.getName() + " - $" + item.getPrice());
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }
    }

    public void addItemToCart(String itemId, int quantity) {
        if (currentCart == null) {
            System.out.println("Please login first.");
            return;
        }
        currentCart.addItem(itemId, quantity);
        System.out.println("Item added to cart.");
    }



    public static void main(String[] args) {
        StoreClient client = new StoreClient();
        client.registerUser("testUser", "testPass", false);
        client.loginUser("testUser", "testPass");
        client.browseItems();
        client.addItemToCart("item1", 2);

    }
}
