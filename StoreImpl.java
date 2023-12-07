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
        inventory = new ArrayList<>();
        initializeInventory();



    }
    @Override
    public String addToCart(String username, String itemId, int quantity) throws RemoteException {
        if (!users.containsKey(username)) {
            return "User not found";
        }


        ShoppingCart cart = userCarts.getOrDefault(username, new ShoppingCart());
        userCarts.putIfAbsent(username, cart);

        Item item = findItemById(itemId);
        if (item != null) {
            if (item.getQuantity() >= quantity) {
                cart.addItem(itemId, quantity);
                return "Item added to cart";
            } else {
                return "Insufficient item quantity. Available: " + item.getQuantity();
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
    @Override
    public String updateItemDetails(String itemId, String newDescription, double newPrice, int newQuantity) throws RemoteException {
        if (itemId == null || itemId.trim().isEmpty()) {
            return "Invalid item ID.";
        }

        for (Item item : inventory) {
            if (item.getId().equals(itemId)) {
                if (newDescription != null && !newDescription.trim().isEmpty()) {
                    item.setDescription(newDescription);
                }
                if (newPrice >= 0) {
                    item.setPrice(newPrice);
                }
                if (newQuantity >= 0) {
                    item.setQuantity(newQuantity);
                }
                return "Item updated successfully.";
            }
        }

        return "Item not found.";
    }
    public void initializeInventory() {
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
    public List<String> checkCart(String username) throws RemoteException {
        ShoppingCart cart = userCarts.get(username);
        if (cart == null) {
            return Collections.singletonList("Cart is empty or user not found.");
        }
        return cart.browseCart();
    }
    @Override
    public List<Item> browseStorage() throws RemoteException {
        return inventory;
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
    public Item getItem(String itemId) throws RemoteException {
        for (Item item : inventory) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }
    @Override
    public String purchaseItems(String username) throws RemoteException {
        ShoppingCart cart = userCarts.get(username);
        if (cart == null || cart.getItems().isEmpty()) {
            return "Your shopping cart is empty.";
        }

        StringBuilder receipt = new StringBuilder();
        double totalCost = 0;

        for (Map.Entry<String, Integer> entry : cart.getItems().entrySet()) {
            String itemId = entry.getKey();
            int quantityToPurchase = entry.getValue();

            Item item = findItemById(itemId);
            if (item == null) {
                receipt.append("Item ID ").append(itemId).append(" not found in inventory.\n");
                continue;
            }

            if (item.getQuantity() < quantityToPurchase) {
                receipt.append("Insufficient quantity for Item ID ").append(itemId)
                        .append(". Available: ").append(item.getQuantity()).append("\n");
                continue;
            }


            item.setQuantity(item.getQuantity() - quantityToPurchase);

            double cost = quantityToPurchase * item.getPrice();
            totalCost += cost;

            receipt.append(quantityToPurchase).append(" x ").append(item.getName())
                    .append(" @ $").append(item.getPrice()).append(" each. Total: $").append(cost).append("\n");
        }
        cart.clearCart();

        if (totalCost == 0) {
            return "Unable to complete purchase. " + receipt.toString();
        }

        receipt.append("Total Cost: $").append(totalCost);
        return "Purchase completed successfully.\n" + receipt.toString();
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
    @Override
    public String removeUser(String username) throws RemoteException {
        if (username == null || username.trim().isEmpty()) {
            return "Invalid username.";
        }
        if (!users.containsKey(username)) {
            return "User not found.";
        }
        users.remove(username);
        return "User removed successfully.";
    }

}
