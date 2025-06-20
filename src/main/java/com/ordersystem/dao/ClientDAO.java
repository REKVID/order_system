package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.Client;

public class ClientDAO {

    public void create(Client client) {
        String sql = "INSERT INTO clients (name, phone, address, contact_person) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getPhone());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getContactPerson());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("ошибка при создании клиента: " + e.getMessage());
        }
    }

    public void update(Client client) {
        String sql = "UPDATE clients SET name = ?, phone = ?, address = ?, contact_person = ? WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getPhone());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getContactPerson());
            stmt.setInt(5, client.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошбка при обновлении клиента: " + e.getMessage());
        }
    }

    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        Client client = null;
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    client = new Client(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("contact_person"));
                }
            }
        } catch (SQLException e) {
            System.err.println("ошибка при поиске клиента по ID: " + e.getMessage());
        }
        return client;
    }

    public void delete(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении клиента: " + e.getMessage());
        }
    }
}
