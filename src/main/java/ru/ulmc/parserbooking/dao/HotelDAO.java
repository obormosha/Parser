package ru.ulmc.parserbooking.dao;

import ru.ulmc.parserbooking.Hotel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class HotelDAO extends CommonDAO {
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            if (!connection.getMetaData().getTables(null, null, "hotels", null).next()) {
                String sql = "CREATE CACHED TABLE hotels (id VARCHAR(34) PRIMARY KEY," +
                        "name VARCHAR(255), " +
                        "address VARCHAR(255), " +
                        "rate VARCHAR(5)," +
                        "link VARCHAR(255) UNIQUE," +
                        "numbersOfRoom VARCHAR(5)," +
                        "type VARCHAR(255), " +
                        "metroStation VARCHAR(255)) " ;
                statement.executeUpdate(sql);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Таблица уже существует, пропускаем...");
        }
    }

    public void insertInTable(Hotel hotel) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO hotels (name, rate, address, link, numbersOfRoom, type, id, metroStation) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            statement.setString(1, hotel.name);
            statement.setString(2, hotel.rate);
            statement.setString(3, hotel.address);
            statement.setString(4, hotel.link);
            statement.setString(5, hotel.numbersOfRoom);
            statement.setString(6, hotel.type);
            statement.setString(7, UUID.randomUUID().toString());
            statement.setString(8, hotel.metroStation);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            if(e.getMessage().contains("integrity constraint violation")) {
                System.out.println("Такая запись уже существует");
            } else {
                e.printStackTrace();
            }
        }
    }


}
