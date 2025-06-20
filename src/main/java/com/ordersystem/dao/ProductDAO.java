package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.Product;

public class ProductDAO {

    public void create(Product product) {
        String sql = "INSERT INTO products (name, price, description, is_delivery_available) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setBoolean(4, product.isDeliveryAvailable());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при создании продукта: " + e.getMessage());
        }
    }

    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, description = ?, is_delivery_available = ? WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setBoolean(4, product.isDeliveryAvailable());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении продукта: " + e.getMessage());
        }
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getString("description"),
                        rs.getBoolean("is_delivery_available"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех продуктов: " + e.getMessage());
        }
        return products;
    }

    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении продукта: " + e.getMessage());
        }
    }
}
