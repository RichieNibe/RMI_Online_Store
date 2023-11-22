import java.io.Serializable;
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String id;
    private String name;
    private double price;
    private String description;

    private int quantity;

    public Item(String id, String name, double price, String description, int quantity){


        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;

    }
    public static String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}