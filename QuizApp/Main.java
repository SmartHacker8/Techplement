import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.close();
        Database db = new Database();
        User user = new User(db);
        Admin admin = new Admin(db);

        System.out.println("Welcome to Quiz App");

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    user.register();
                    break;
                case 2:
                    if (user.login()) {
                        user.takeQuiz();
                    }
                    break;
                case 3:
                    if (admin.login()) {
                        admin.adminPanel();
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
