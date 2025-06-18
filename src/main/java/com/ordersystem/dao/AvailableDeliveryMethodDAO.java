package com.ordersystem.dao;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.AvailableDeliveryMethod;

import java.sql.*;
import java.util.*;

public class AvailableDeliveryMethodDAO {

    public void save(AvailableDeliveryMethod availableDeliveryMethod) {
        String sql = "INSERT INTO available_delivery_Methods (product_id, delivery_method_id, delivery_cost) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE delivery_cost = VALUES(delivery_cost)";

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, availableDeliveryMethod.getProductId());
            stmt.setInt(2, availableDeliveryMethod.getDeliveryMethodId());
            stmt.setBigDecimal(3, availableDeliveryMethod.getDeliveryCost());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении доступного метода доставки: " + e.getMessage());
        }
    }

    public List<AvailableDeliveryMethod> findByProductId(int productId) {
        String sql = "SELECT * FROM available_delivery_Methods WHERE product_id = ?";
        List<AvailableDeliveryMethod> methods = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AvailableDeliveryMethod method = new AvailableDeliveryMethod(
                            rs.getInt("product_id"),
                            rs.getInt("delivery_method_id"),
                            rs.getBigDecimal("delivery_cost"));
                    methods.add(method);
                }
            }
        } catch (SQLException e) {
            System.err.println("ошибка поиска доступных методов доставки по ID продукта: " + e.getMessage());
        }
        return methods;
    }

    public void delete(int productId, int deliveryMethodId) {
        String sql = "DELETE FROM available_delivery_Methods WHERE product_id = ? AND delivery_method_id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, deliveryMethodId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка удаления доступного метода доставки: " + e.getMessage());
        }
    }
}
