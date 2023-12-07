import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StoreInterface extends Remote {


    String addItem(Item item) throws RemoteException;


    String removeItem(String itemId) throws RemoteException;

    List<String> checkCart(String username) throws RemoteException;

    List<Item>browseStorage() throws RemoteException;


    String addToCart(String username, String itemId, int quantity) throws RemoteException;

    String registerUser(String username, String password, boolean isAdmin) throws RemoteException;

    String loginUser(String username, String password) throws RemoteException;

    Item getItem(String itemId) throws RemoteException;



    String updateItemDetails(String itemId, String newDescription, double newPrice, int newQuantity) throws RemoteException;

    String removeUser(String username) throws RemoteException;

    String purchaseItems(String currentUser) throws RemoteException;
}

