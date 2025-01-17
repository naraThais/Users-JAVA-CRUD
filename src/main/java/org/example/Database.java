package org.example;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:users.db"; // Caminho para o banco de dados SQLite

    // Método para conectar ao banco de dados
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Método para criar a tabela de usuários
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, email TEXT)";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para inserir um novo usuário no banco
    public static void insertUser(User user) {
        String sql = "INSERT INTO users(name, email) VALUES(?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para obter todos os usuários do banco de dados
    public static String getUsers() {
        String sql = "SELECT * FROM users";
        StringBuilder users = new StringBuilder("[");
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.append("{\"id\": ").append(rs.getInt("id"))
                        .append(", \"name\": \"").append(rs.getString("name"))
                        .append("\", \"email\": \"").append(rs.getString("email"))
                        .append("\"}, ");
            }
            if (users.length() > 1) users.setLength(users.length() - 2); // Remove a última vírgula
            users.append("]");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users.toString();
    }

    // Método para atualizar um usuário
    public static void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para excluir um usuário
    public static void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
