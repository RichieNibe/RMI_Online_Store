import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.*;

public class StoreImpl extends UnicastRemoteObject implements StoreInterface {
    private List<Item> inventory;
    private Map<String, User> users;
    private Map<String, ShoppingCart> userCarts;
    protected StoreImpl() throws RemoteException {
        super();
        users = new HashMap<>();
        userCarts = new HashMap<>();
        initializeInventory();
        inventory = new ArrayList<>();



    }
    @Override
    public String addToCart(String username, String itemId, int quantity) throws RemoteException {
        if (!users.containsKey(username)) {
            return "User not found";
        }


        ShoppingCart cart = userCarts.getOrDefault(username, new ShoppingCart());
        userCarts.putIfAbsent(username, cart);

        for (Item item : inventory) {
            if (item.getId().equals(itemId)) {
                if (item.getQuantity() >= quantity) {
                    cart.addItem(itemId, quantity);
                    return "Item added to cart";
                } else {
                    return "Insufficient item quantity";
                }
            }
        }

        return "Item not found";
    }

    @Override
    public String addItem(Item newItem) throws RemoteException {
        if (newItem == null || inventory == null) {
            return "Invalid item or uninitialized inventory";
        }

        Optional<Item> existingItem = inventory.stream()
                .filter(item -> item != null && item.getId().equals(newItem.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            Item foundItem = existingItem.get();
            foundItem.setQuantity(foundItem.getQuantity() + newItem.getQuantity());
            return "Item quantity updated";
        } else {
            inventory.add(newItem);
            return "Item added successfully";
        }
    }
    private void initializeInventory() {
        inventory = new ArrayList<>();
        inventory.add(new Item("item1", "Orange", 20, "Ripe Orange", 1000));
        inventory.add(new Item("item4", "Watermelon", 20, "Fresh watermelon", 1000));
        inventory.add(new Item("1", "Apple", 0.5, "Fresh Apple", 100));
        inventory.add(new Item("2", "Banana", 0.3, "Ripe Banana", 150));

    }

    @Override
    public String removeItem(String itemId) throws RemoteException {
        Iterator<Item> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getId().equals(itemId)) {
                iterator.remove();
                return "Item removed successfully";
            }
        }
        return "Item not found";
    }

    @Override
    public List<Item> browseItems() throws RemoteException {
        return new ArrayList<>(inventory);
    }

    @Override
    public String registerUser(String username, String password, boolean isAdmin) throws RemoteException {
        if (users.containsKey(username)) {
            return "Username already exists";
        }


        users.put(username, new User(username, password, isAdmin));
        return "User registered successfully";
    }

    @Override
    public String purchaseItems(User user, ShoppingCart cart) throws RemoteException {

        return null;
    }
    public void updateItem(String itemId, Item updatedItem) throws RemoteException {

    }
    public String addToCart(ShoppingCart cart, String itemId, int quantity) throws RemoteException {
        Item item = findItemById(itemId);
        if (item == null) {
            return "Item not found";
        }
        if (item.getQuantity() < quantity) {
            return "Not enough stock available";
        }

        cart.addItem(itemId, quantity);
        return "Item added to cart";
    }

    private Item findItemById(String itemId) {
        for (Item item : inventory) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }
    @Override
    public String loginUser(String username, String password) throws RemoteException {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {

            boolean isUserAdmin = user.isAdmin();
            if (isUserAdmin ) {
                return "Admin login successful";
            } else {
                return "Customer login successful";
            }
        }
        return "Invalid username or password";
    }

}
