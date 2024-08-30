package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db {

    private static Db instance;
    private Connection connection;
    private String dbname;
    private String user;
    private String pass;

    //constructor
    private Db(String dbname, String user, String pass) {
        this.dbname = dbname;
        this.user = user;
        this.pass = pass;

        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (this.connection != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Public method to provide access to the Singleton instance
    public static Db getInstance(String dbname, String user, String pass) {
        if (instance == null) {
            instance = new Db(dbname, user, pass);
        }
        return instance;
    }

    // Method to get the connection
    public Connection getConnection() {
        return connection;
    }
}
