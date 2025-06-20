package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ordersystem.db.DatabaseManager;
import com.ordersystem.model.Document;

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

    public List<Document> findAll() {
        String sql = "SELECT * FROM document ORDER BY date DESC";
        List<Document> documents = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Document document = new Document(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getDate("date"));
                documents.add(document);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех документов: " + e.getMessage());
        }
        return documents;
    }
}
