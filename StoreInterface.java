import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StoreInterface extends Remote {


    String addItem(Item item) throws RemoteException;

    String removeItem(String itemId) throws RemoteException;

    List<Item> browseItems() throws RemoteException;

    String registerUser(String username, String password, boolean isAdmin) throws RemoteException;

    User loginUser(String username, String password) throws RemoteException;
    void updateItem(String itemId, Item updatedItem) throws RemoteException;
    String purchaseItems(User user, ShoppingCart cart) throws RemoteException;



}

