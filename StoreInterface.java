import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StoreInterface extends Remote {
    String registerUser(String username, String password) throws RemoteException;

    String addItem(Item item) throws RemoteException;

    String removeItem(String itemId) throws RemoteException;

    List<Item> browseItems() throws RemoteException;


    // Other remote methods
}

