package ru.ulmc.parserbooking.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonDAO {
    protected static Connection connection;

    public void open() throws SQLException {
        connection = DriverManager.getConnection("jdbc:hsqldb:file:parseDB", "SA", "");
    }

    public void close() throws SQLException {
        try {
            Statement statement = connection.createStatement();
            String sql = "SHUTDOWN";
            statement.execute(sql);
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
