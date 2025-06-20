package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.DeliveryMethod;

public class DeliveryMethodDAO {

    public void create(DeliveryMethod deliveryMethod) {
        String sql = "INSERT INTO delivery_methods (name, speed_days) VALUES (?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, deliveryMethod.getName());
            stmt.setInt(2, deliveryMethod.getSpeedDays());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    deliveryMethod.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("ошибка при создании метода доставки: " + e.getMessage());
        }
    }

    public void update(DeliveryMethod deliveryMethod) {
        String sql = "UPDATE delivery_methods SET name = ?, speed_days = ? WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, deliveryMethod.getName());
            stmt.setInt(2, deliveryMethod.getSpeedDays());
            stmt.setInt(3, deliveryMethod.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ошибка при обновлении метода доставки: " + e.getMessage());
        }
    }

    public DeliveryMethod findById(int id) {
        String sql = "SELECT * FROM delivery_methods WHERE id = ?";
        DeliveryMethod deliveryMethod = null;
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    deliveryMethod = new DeliveryMethod(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("speed_days"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске метода доставки по ID: " + e.getMessage());
        }
        return deliveryMethod;
    }

    public List<DeliveryMethod> findAll() {
        String sql = "SELECT * FROM delivery_methods";
        List<DeliveryMethod> deliveryMethods = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DeliveryMethod deliveryMethod = new DeliveryMethod(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("speed_days"));
                deliveryMethods.add(deliveryMethod);
            }
        } catch (SQLException e) {
            System.err.println("ошибка при получении всех методов доставки: " + e.getMessage());
        }
        return deliveryMethods;
    }

    public void delete(int id) {
        String sql = "DELETE FROM delivery_methods WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении метода доставки: " + e.getMessage());
        }
    }
}
