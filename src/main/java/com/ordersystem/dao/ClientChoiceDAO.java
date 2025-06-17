package com.ordersystem.dao;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.ClientChoice;

import java.sql.*;
import java.util.*;

public class ClientChoiceDAO {

    public void create(ClientChoice clientChoice) {
        String sql = "INSERT INTO clientsChoise (document_id, product_id, delivery_Methods_id, quantity) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, clientChoice.getDocumentId());
            stmt.setInt(2, clientChoice.getProductId());
            if (clientChoice.getDeliveryMethodsId() != null) {
                stmt.setInt(3, clientChoice.getDeliveryMethodsId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            stmt.setInt(4, clientChoice.getQuantity());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    clientChoice.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при создании выбора клиента: " + e.getMessage());
        }
    }

    public void update(ClientChoice clientChoice) {
        String sql = "UPDATE clientsChoise SET document_id = ?, product_id = ?, delivery_Methods_id = ?, quantity = ? WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientChoice.getDocumentId());
            stmt.setInt(2, clientChoice.getProductId());
            if (clientChoice.getDeliveryMethodsId() != null) {
                stmt.setInt(3, clientChoice.getDeliveryMethodsId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            stmt.setInt(4, clientChoice.getQuantity());
            stmt.setInt(5, clientChoice.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении выбора клиента: " + e.getMessage());
        }
    }

    public ClientChoice findById(int id) {
        String sql = "SELECT * FROM clientsChoise WHERE id = ?";
        ClientChoice clientChoice = null;
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    clientChoice = new ClientChoice(
                            rs.getInt("id"),
                            rs.getInt("document_id"),
                            rs.getInt("product_id"),
                            rs.getObject("delivery_Methods_id", Integer.class), // Для nullable Integer
                            rs.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске выбора клиента по ID: " + e.getMessage());
        }
        return clientChoice;
    }

    public List<ClientChoice> findAll() {
        String sql = "SELECT * FROM clientsChoise";
        List<ClientChoice> clientChoices = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClientChoice clientChoice = new ClientChoice(
                        rs.getInt("id"),
                        rs.getInt("document_id"),
                        rs.getInt("product_id"),
                        rs.getObject("delivery_Methods_id", Integer.class),
                        rs.getInt("quantity"));
                clientChoices.add(clientChoice);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех выборов клиента: " + e.getMessage());
        }
        return clientChoices;
    }

    public void delete(int id) {
        String sql = "DELETE FROM clientsChoise WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении выбора клиента: " + e.getMessage());
        }
    }
}
