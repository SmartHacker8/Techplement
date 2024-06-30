import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection;

    public Database() {
        try {
            // Load the SQLite JDBC driver (optional step for modern JDBC drivers)
            Class.forName("org.sqlite.JDBC");

            // Establish a connection to the SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:quizapp.db");
            System.out.println("Connection to SQLite has been established.");
            createTables();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        }
    }

    private void createTables() {
        String userTable = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
        String adminTable = "CREATE TABLE IF NOT EXISTS admins (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
        String quizTable = "CREATE TABLE IF NOT EXISTS quiz (id INTEGER PRIMARY KEY, question TEXT, option1 TEXT, option2 TEXT, option3 TEXT, option4 TEXT, answer TEXT)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(userTable);
            stmt.execute(adminTable);
            stmt.execute(quizTable);
        } catch (SQLException e) {
            System.err.println("Failed to create tables.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
