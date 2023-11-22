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

    public void registerUser() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            String response = storeStub.registerUser(username, password, true);
            System.out.println(response);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void loginUser() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            int password = Integer.parseInt(scanner.nextLine());
            System.out.println("Are you an Admin:");
            boolean Admin = Boolean.parseBoolean(scanner.nextLine());


            String response = storeStub.loginUser(username, String.valueOf(password), Admin);
            System.out.println(response);
            if (response.equals("Login successful")) {
                currentUser = username;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addToCart() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
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

    public static void main(String[] args) {
        StoreClient client = new StoreClient();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add to Cart");
            System.out.println("4. Display Cart");
            System.out.println("5. Exit");
            System.out.println("Choose an option:");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                if (scanner.hasNext()) {
                    scanner.nextLine();
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                } else {
                    System.out.println("No input provided. Exiting.");
                    break;
                }
            }


            switch (choice) {
                case 1:
                    client.registerUser();
                    break;
                case 2:
                    client.loginUser();
                    break;
                case 3:
                    client.addToCart();
                    break;
                case 4:
                    client.displayCart();
                    break;
                case 5:
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
