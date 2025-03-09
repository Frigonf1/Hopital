package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDriver {
    public static void main (String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:"  )) {
            System.out.println("Connected to the database successfully");
        }
        catch(
               SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
