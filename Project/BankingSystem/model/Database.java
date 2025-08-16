package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:bank.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "  id INTEGER PRIMARY KEY," +
                "  username TEXT UNIQUE," +
                "  password TEXT," +
                "  role TEXT" +
                ")"
            );

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS accounts (" +
                "  id INTEGER PRIMARY KEY," +
                "  owner TEXT UNIQUE," +
                "  balance REAL" +
                ")"
            );

            stmt.execute(
                "INSERT OR IGNORE INTO users (username, password, role) " +
                "VALUES ('admin','admin','Admin')"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}