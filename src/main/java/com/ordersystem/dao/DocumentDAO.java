package com.ordersystem.dao;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.Document;

import java.sql.*;
import java.util.*;

public class DocumentDAO {

    public int create(Document document) {
        String sql = "INSERT INTO document (client_id, date) VALUES (?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();
        int documentId = -1;
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, document.getClientId());
            stmt.setDate(2, document.getDate());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    documentId = generatedKeys.getInt(1);
                    document.setId(documentId);
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при создании документа: " + e.getMessage());
        }
        return documentId;
    }

    public void update(Document document) {
        String sql = "UPDATE document SET client_id = ?, date = ? WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, document.getClientId());
            stmt.setDate(2, document.getDate());
            stmt.setInt(3, document.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении документа: " + e.getMessage());
        }
    }

    public Document findById(int id) {
        String sql = "SELECT * FROM document WHERE id = ?";
        Document document = null;
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    document = new Document(
                            rs.getInt("id"),
                            rs.getInt("client_id"),
                            rs.getDate("date"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения документа по ID: " + e.getMessage());
        }
        return document;
    }

    public List<Document> findAllByClientId(int clientId) {
        String sql = "SELECT * FROM document WHERE client_id = ?";
        List<Document> documents = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Document document = new Document(
                            rs.getInt("id"),
                            rs.getInt("client_id"),
                            rs.getDate("date"));
                    documents.add(document);
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения документов клиента: " + e.getMessage());
        }
        return documents;
    }

    public void delete(int id) {
        String sql = "DELETE FROM document WHERE id = ?";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении документа: " + e.getMessage());
        }
    }
}
