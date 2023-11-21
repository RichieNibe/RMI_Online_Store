import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StoreImpl extends UnicastRemoteObject implements StoreInterface {
    private List<Item> inventory;
    private Map<String, User> users;
    protected StoreImpl() throws RemoteException {
        super();
        inventory = new ArrayList<>();

    }
    @Override
    public String addItem(Item item) throws RemoteException {

        Optional<Item> existingItem = inventory.stream()
                .filter(i -> i.getId().equals(item.getId()))
                .findFirst();

        if (existingItem.isPresent()) {

            Item foundItem = existingItem.get();
            foundItem.setQuantity(foundItem.getQuantity() + item.getQuantity());
            return "Item quantity updated";
        } else {

            inventory.add(item);

        return "Item added successfully";
        }
    }

    @Override
    public String removeItem(String itemId) throws RemoteException {
        for (Item item : inventory) {
            if (Item.getId().equals(itemId)) {
                inventory.remove(item);
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
    public String registerUser(String username, String password) throws RemoteException {
        if (users.containsKey(username)) {
            return "Username already exists";
        }

        users.put(username, new User(username, password));
        return "User registered successfully";
    }

}
