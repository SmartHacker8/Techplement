import java.sql.*;
import java.util.Scanner;

public class User {
    private Database db;
    private Scanner scanner = new Scanner(System.in);

    public User(Database db) {
        this.db = db;
    }

    public void register() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        try (PreparedStatement pstmt = db.getConnection().prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("Registration successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        try (PreparedStatement pstmt = db.getConnection().prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful");
                return true;
            } else {
                System.out.println("Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void takeQuiz() {
        try (Statement stmt = db.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM quiz");
            int score = 0;
            while (rs.next()) {
                System.out.println(rs.getString("question"));
                System.out.println("1. " + rs.getString("option1"));
                System.out.println("2. " + rs.getString("option2"));
                System.out.println("3. " + rs.getString("option3"));
                System.out.println("4. " + rs.getString("option4"));
                System.out.print("Enter your answer (1-4): ");
                int userAnswerIndex = scanner.nextInt();
                String userAnswer = "";
                switch (userAnswerIndex) {
                    case 1:
                        userAnswer = rs.getString("option1");
                        break;
                    case 2:
                        userAnswer = rs.getString("option2");
                        break;
                    case 3:
                        userAnswer = rs.getString("option3");
                        break;
                    case 4:
                        userAnswer = rs.getString("option4");
                        break;
                    default:
                        System.out.println("Invalid choice. Skipping question.");
                        continue; // Skip to next question
                }
                if (userAnswer.equalsIgnoreCase(rs.getString("answer"))) {
                    score++;
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect. The correct answer is: " + rs.getString("answer"));
                }
            }
            System.out.println("Your score: " + score);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
