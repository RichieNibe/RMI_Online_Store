import java.io.Serializable;
import java.util.HashMap;
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

        items.merge(itemId, quantity, Integer::sum); // Add or update the item's quantity
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
 
}


