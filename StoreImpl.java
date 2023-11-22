import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.*;

public class StoreImpl extends UnicastRemoteObject implements StoreInterface {
    private List<Item> inventory;
    private Map<String, User> users;
    protected StoreImpl() throws RemoteException {
        super();
        users = new HashMap<>();
        inventory = new ArrayList<>();


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
    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

}
