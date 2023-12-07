import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class StoreClient {
    private StoreInterface storeStub;
    private ShoppingCart currentCart;
    private String currentUser;

    public StoreClient() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            storeStub = (StoreInterface) registry.lookup("Store");
            currentCart = new ShoppingCart();
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
            System.out.println("Are you an Admin true/false (response is case sensitive):");
            boolean admin = scanner.nextBoolean();

            String response = storeStub.registerUser(username, password, admin);
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

    public void addToCart(Scanner scanner) {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        try  {
            System.out.println("Enter Item ID:");
            String itemId = scanner.nextLine();
            System.out.println("Enter quantity:");
            int quantity = Integer.parseInt(scanner.nextLine());

            String response = storeStub.addToCart(currentUser, itemId, quantity);
            System.out.println(response);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public void displayCart() {
        currentCart.browseItems();
    }
    public void adminMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Admin Menu:");
            System.out.println("1. Manage Inventory");
            System.out.println("2. View Reports");
            System.out.println("3. Logout");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:

                    System.out.println("Managing Inventory...");
                    break;
                case 2:

                    System.out.println("Viewing Reports...");
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    public void customerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Customer Menu:");
            System.out.println("1. Browse Items");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Logout");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:

                    System.out.println("Browsing Items...");
                    break;
                case 2:
                    addToCart(scanner);
                    break;
                case 3:
                    displayCart();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    public static void main(String[] args) {
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
