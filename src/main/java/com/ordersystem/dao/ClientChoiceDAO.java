package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.ClientChoice;

public class ClientChoiceDAO {

    public void create(ClientChoice clientChoice) {
        String sql = "INSERT INTO client_choices (document_id, product_id, delivery_method_id, quantity) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, clientChoice.getDocumentId());
            stmt.setInt(2, clientChoice.getProductId());
            if (clientChoice.getDeliveryMethodId() == null) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, clientChoice.getDeliveryMethodId());
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

    public List<ClientChoice> findByDocumentId(int documentId) {
        String sql = "SELECT " +
                "cc.id, cc.document_id, cc.product_id, cc.delivery_method_id, cc.quantity, " +
                "p.name AS product_name, p.price AS product_price, " +
                "dm.name AS delivery_method_name, " +
                "adm.delivery_cost " +
                "FROM client_choices cc " +
                "JOIN products p ON cc.product_id = p.id " +
                "LEFT JOIN delivery_methods dm ON cc.delivery_method_id = dm.id " +
                "LEFT JOIN available_delivery_methods adm ON cc.product_id = adm.product_id AND cc.delivery_method_id = adm.delivery_method_id "
                +
                "WHERE cc.document_id = ?";
        List<ClientChoice> choices = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, documentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClientChoice choice = new ClientChoice(
                            rs.getInt("id"),
                            rs.getInt("document_id"),
                            rs.getInt("product_id"),
                            (Integer) rs.getObject("delivery_method_id"),
                            rs.getInt("quantity"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("product_price"),
                            rs.getString("delivery_method_name"),
                            rs.getBigDecimal("delivery_cost"));
                    choices.add(choice);
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске выборов клиента по ID документа: " + e.getMessage());
        }
        return choices;
    }
}
