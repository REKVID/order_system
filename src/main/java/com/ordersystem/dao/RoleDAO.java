package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.Role;

public class RoleDAO {



    public Role findById(int id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        Role role = null;

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = new Role(
                            rs.getInt("id"),
                            rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка поиска роли : " + e.getMessage());
        }
        return role;
    }

    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        List<Role> roles = new ArrayList<>();

        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Role role = new Role(
                        rs.getInt("id"),
                        rs.getString("name"));
                roles.add(role);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка поиска ролей" + e.getMessage());
        }
        return roles;
    }
}