import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShoppingCart implements Serializable {
    private Map<Item, Integer> items; 

    public ShoppingCart() {
        items = new HashMap<>();
    }

    @Override
    public String addItem(Item newItem) throws RemoteException {
        if (newItem == null || items == null) {
            return "Invalid item or uninitialized inventory";
        }

        Optional<Item> existingItem = items.size()
                .filter(item -> item != null && Item.getId().equals(newItem.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            Item foundItem = existingItem.get();
            foundItem.setQuantity(foundItem.getQuantity() + newItem.getQuantity());
            return "Item quantity updated";
        } else {
            items.add(newItem);
            return "Item added successfully";
        }
    }
 
}


