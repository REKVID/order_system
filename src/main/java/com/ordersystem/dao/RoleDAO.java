package com.ordersystem.dao;

import com.ordersystem.model.Role;
import com.ordersystem.db.DatabaseManager;

import java.sql.*;

public class RoleDAO {

    public void create(Role role) {
        String sql = "INSERT INTO roles (name) VALUES (?)";

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, role.getName());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка создания роли: " + e.getMessage());
        }
    }

    public void update(Role role) {
        String sql = "UPDATE roles SET name = ? WHERE id = ?";

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role.getName());
            stmt.setInt(2, role.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка обновления роли: " + e.getMessage());
        }
    }

    public Role findById(int id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        Role role = null;

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = new Role(
                            rs.getInt("id"),
                            rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка поиска роли по id: " + e.getMessage());
        }
        return role;
    }

    public void delete(int id) {
        String sql = "DELETE FROM roles WHERE id = ?";

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении роли: " + e.getMessage());
        }
    }
}
