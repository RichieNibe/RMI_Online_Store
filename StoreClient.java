import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class StoreClient {
    private StoreInterface storeStub;


    private String currentUser;

    public StoreClient() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            storeStub = (StoreInterface) registry.lookup("Store");
            storeStub.registerUser("Richard","123",true);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void registerUser(Scanner scanner) {
        try  {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();


            String response = storeStub.registerUser(username, password, false);
            System.out.println(response);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public String loginUser(Scanner scanner) {
        try {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();


            String response = storeStub.loginUser(username, password);
            System.out.println(response);

            if (response.equals("Admin login successful")) {
                currentUser = username;
                return "admin";
            } else if (response.equals("Customer login successful")) {
                currentUser = username;
                return "customer";
            } else {
                return "unauthorized";
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return "unauthorized";
        }
    }

    public void addToCart(Scanner scanner) throws RemoteException {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        System.out.println("Enter Item ID:");
        String itemId = scanner.nextLine();
        System.out.println("Enter quantity:");
        int quantity = Integer.parseInt(scanner.nextLine());

        String response = storeStub.addToCart(currentUser, itemId, quantity);
        System.out.println(response);
    }


    public void displayCart() throws RemoteException {
        List<String> cartContents = storeStub.checkCart(currentUser);
        for (String content : cartContents) {
            System.out.println(content);
        }
    }


    public void displayInventory() throws RemoteException {
        List<Item> inventory = storeStub.browseStorage();
        if (inventory.isEmpty()) {
            System.out.println("The inventory is empty.");
        } else {
            for (Item item : inventory) {
                System.out.println("Item ID: " + item.getId() + ", Name: " + item.getName()
                        + ", Price: " + item.getPrice() + ", Description: "
                        + item.getDescription() + ", Quantity: " + item.getQuantity());
            }
        }
    }
    public void purchaseItems() throws RemoteException {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        String response = storeStub.purchaseItems(currentUser );
        System.out.println(response);
    }

    public void updateItem(Scanner scanner) throws RemoteException {
        System.out.println("Enter Item ID to update:");
        String itemId = scanner.nextLine();
        System.out.println("Enter new description (press Enter to skip):");
        String newDescription = scanner.nextLine();
        System.out.println("Enter new price :");
        double newPrice = scanner.nextDouble();
        System.out.println("Enter new quantity :");
        int newQuantity = scanner.nextInt();


        String response = storeStub.updateItemDetails( itemId, newDescription, newPrice, newQuantity);
        System.out.println(response);
    }
    public void manageUsers(Scanner scanner) throws RemoteException {
        System.out.println("1. Add User");
        System.out.println("2. Remove User");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                addUser(scanner);
                break;
            case 2:
                removeUser(scanner);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public void addUser(Scanner scanner) throws RemoteException {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Is Admin? (true/false):");
        boolean isAdmin = Boolean.parseBoolean(scanner.nextLine());


        String response = storeStub.registerUser(username, password, isAdmin);
        System.out.println(response);
    }

    public void removeUser(Scanner scanner) throws RemoteException {
        System.out.println("Enter username to remove:");
        String username = scanner.nextLine();


        String response = storeStub.removeUser(username);
        System.out.println(response);
    }


    public void adminMenu(Scanner scanner) throws RemoteException {
        boolean running = true;
        while (running) {
            System.out.println("Admin Menu:");
            System.out.println("1. View Inventory");
            System.out.println("2. Update Item");
            System.out.println("3. Add/Remove User");
            System.out.println("4. Logout");


            System.out.print("Choose an option: ");
            String input = scanner.nextLine();


            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    displayInventory();
                    break;
                case 2:
                    updateItem(scanner);
                    break;
                case 3:
                    manageUsers(scanner);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    public void customerMenu(Scanner scanner) throws RemoteException {
        boolean running = true;
        while (running) {
            System.out.println("Customer Menu:");
            System.out.println("1. Browse Items");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Logout");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Browsing Items");
                    displayInventory();
                    break;
                case 2:
                    addToCart(scanner);
                    break;
                case 3:
                    displayCart();
                    break;
                case 4:
                    purchaseItems();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    public static void main(String[] args) throws RemoteException {
        StoreClient client = new StoreClient();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Choose an option:");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {

                    scanner.nextLine();
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
            }



            switch (choice) {

                case 1:
                    client.registerUser( scanner);
                    break;
                case 2:
                    String userRole = client.loginUser(scanner);
                    if (userRole.equals("admin")) {
                        client.adminMenu(scanner);
                    } else if (userRole.equals("customer")) {
                        client.customerMenu(scanner);
                    } else {
                        System.out.println("Unauthorized access or login failed.");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
