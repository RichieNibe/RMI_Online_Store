import java.io.Serializable;
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
        items.put(itemId, items.getOrDefault(itemId, 0) + quantity);
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

    public List<String> browseCart() {
        List<String> cartContents = new ArrayList<>();
        if (items.isEmpty()) {
            cartContents.add("The shopping cart is empty.");
        } else {
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                String itemDetail = "Item ID: " + entry.getKey() + ", Quantity: " + entry.getValue();
                cartContents.add(itemDetail);
            }
        }
        return cartContents;
    }

}


