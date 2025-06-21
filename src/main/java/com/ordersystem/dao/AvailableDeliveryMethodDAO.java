package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.AvailableDeliveryMethod;

public class AvailableDeliveryMethodDAO {

    public List<AvailableDeliveryMethod> findAll() {
        String sql = "SELECT * FROM available_delivery_methods";
        List<AvailableDeliveryMethod> methods = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AvailableDeliveryMethod method = new AvailableDeliveryMethod(
                        rs.getInt("product_id"),
                        rs.getInt("delivery_method_id"),
                        rs.getBigDecimal("delivery_cost"));
                methods.add(method);
            }
        } catch (SQLException e) {
            System.err.println("ошибка поиска всех доступных методов доставки: " + e.getMessage());
        }
        return methods;
    }

    public void save(AvailableDeliveryMethod availableDeliveryMethod) {
        String sql = "INSERT INTO available_delivery_methods (product_id, delivery_method_id, delivery_cost) " +
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
        String sql = "SELECT adm.*, dm.name as delivery_method_name FROM available_delivery_methods adm " +
                "JOIN delivery_methods dm ON adm.delivery_method_id = dm.id " +
                "WHERE adm.product_id = ?";
        List<AvailableDeliveryMethod> methods = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AvailableDeliveryMethod method = new AvailableDeliveryMethod(
                            rs.getInt("product_id"),
                            rs.getInt("delivery_method_id"),
                            rs.getBigDecimal("delivery_cost"),
                            rs.getString("delivery_method_name"));
                    methods.add(method);
                }
            }
        } catch (SQLException e) {
            System.err.println("ошибка поиска доступных методов доставки по ID продукта: " + e.getMessage());
        }
        return methods;
    }

    public void delete(int productId, int deliveryMethodId) {
        String sql = "DELETE FROM available_delivery_methods WHERE product_id = ? AND delivery_method_id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, deliveryMethodId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка удаления доступного метода доставки: " + e.getMessage());
        }
    }

    public void deleteByProductId(int productId) {
        String sql = "DELETE FROM available_delivery_methods WHERE product_id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка удаления ДМД по ID продукта: " + e.getMessage());
        }
    }

}
