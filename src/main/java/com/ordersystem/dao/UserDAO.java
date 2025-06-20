package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.User;

public class UserDAO {

    public void create(User user) {
        String sql = "INSERT INTO users (username, password_hash, role_id, client_id) VALUES (?, ?, ?, ?)";

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRoleId());

            if (user.getClientId() != null) {
                stmt.setInt(4, user.getClientId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при создании пользователя: " + e.getMessage());
        }
    }

    public void update(User user) {
        String sql = "UPDATE users SET username = ?, password_hash = ?, role_id = ?, client_id = ? WHERE id = ?";

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRoleId());
            if (user.getClientId() != null) {
                stmt.setInt(4, user.getClientId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            stmt.setInt(5, user.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении пользователя: " + e.getMessage());
        }
    }

    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getInt("role_id"),
                            rs.getObject("client_id", Integer.class) // Для nullable Integer
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка поиска пользователя по id: " + e.getMessage());
        }
        return user;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getInt("role_id"),
                            rs.getObject("client_id", Integer.class));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка поиска пользователя по имени пользователя: " + e.getMessage());
        }
        return user;
    }

    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка удалени пользователя: " + e.getMessage());
        }
    }

    public boolean authenticateUser(String username, String password) {
        User user = findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
