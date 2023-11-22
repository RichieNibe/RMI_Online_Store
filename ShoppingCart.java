import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private Map<String, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }


    public void addItem(String itemId, int quantity) {
        if (itemId == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid item ID or quantity");
        }

        items.put(itemId, quantity);
    }

    public void removeItem(String itemId) {
        if (!items.containsKey(itemId)) {
            throw new IllegalArgumentException("Item  not found in cart");
        }
        items.remove(itemId);
    }
    public void clearCart() {
        items.clear();
    }

    public void browseItems() {
        if (items.isEmpty()) {
            System.out.println("The shopping cart is empty.");
        } else {
            System.out.println("Items in the shopping cart:");
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                System.out.println("Item ID: " + entry.getKey() + ", Quantity: " + entry.getValue());
            }
        }
    }
}


