import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StoreInterface extends Remote {


    String addItem(Item item) throws RemoteException;

    String removeItem(String itemId) throws RemoteException;

    List<Item> browseItems() throws RemoteException;

    String addToCart(String username, String itemId, int quantity) throws RemoteException;

    String registerUser(String username, String password, boolean isAdmin) throws RemoteException;

    String loginUser(String username, String password, boolean isAdmin) throws RemoteException;
    void updateItem(String itemId, Item updatedItem) throws RemoteException;
    String purchaseItems(User user, ShoppingCart cart) throws RemoteException;



}

