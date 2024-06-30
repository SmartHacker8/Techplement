import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
    private Database db;
    private Scanner scanner = new Scanner(System.in);

    public Admin(Database db) {
        this.db = db;
    }

    public boolean login() {
        System.out.print("Enter admin username: ");
        String username = scanner.next();
        System.out.print("Enter admin password: ");
        String password = scanner.next();

        Connection connection = db.getConnection();
        if (connection == null) {
            System.err.println("Database connection is null.");
            return false;
        }

        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM admins WHERE username = ? AND password = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Admin login successful");
                return true;
            } else {
                System.out.println("Invalid admin credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void adminPanel() {
        while (true) {
            System.out.println("1. Add Quiz");
            System.out.println("2. Delete Quiz");
            System.out.println("3. Edit Quiz");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addQuiz();
                    break;
                case 2:
                    deleteQuiz();
                    break;
                case 3:
                    editQuiz();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addQuiz() {
        System.out.print("Enter question: ");
        scanner.nextLine();
        String question = scanner.nextLine();
        System.out.print("Enter answer: ");
        String answer = scanner.next();

        try (PreparedStatement pstmt = db.getConnection().prepareStatement("INSERT INTO quiz (question, answer) VALUES (?, ?)")) {
            pstmt.setString(1, question);
            pstmt.setString(2, answer);
            pstmt.executeUpdate();
            System.out.println("Quiz added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteQuiz() {
        System.out.print("Enter quiz ID to delete: ");
        int id = scanner.nextInt();

        try (PreparedStatement pstmt = db.getConnection().prepareStatement("DELETE FROM quiz WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Quiz deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editQuiz() {
        System.out.print("Enter quiz ID to edit: ");
        int id = scanner.nextInt();
        System.out.print("Enter new question: ");
        scanner.nextLine();
        String question = scanner.nextLine();
        System.out.print("Enter new answer: ");
        String answer = scanner.next();

        try (PreparedStatement pstmt = db.getConnection().prepareStatement("UPDATE quiz SET question = ?, answer = ? WHERE id = ?")) {
            pstmt.setString(1, question);
            pstmt.setString(2, answer);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Quiz updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
