package org.example;

import java.sql.*;

public class LibraryApp {

    public static void main(String[] args) {

        // Database details
        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String password = "Raja@2012";

        try (Connection con =
                     DriverManager.getConnection(url, username, password)) {

            // ==========================================
            // TASK 1: CONNECT TO DATABASE
            // ==========================================

            System.out.println("Connected to database");


            // ==========================================
            // TASK 2: DISPLAY ALL BOOKS
            // ==========================================

            System.out.println("\n--- All Books ---");

            String selectSql = "SELECT * FROM book";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(selectSql)) {

                while (rs.next()) {

                    System.out.println(
                            rs.getInt("book_id") + " | " +
                                    rs.getString("title") + " | " +
                                    rs.getString("author") + " | " +
                                    rs.getDouble("price")
                    );
                }
            }


            // ==========================================
            // TASK 3: INSERT NEW BOOK
            // ==========================================

            String insertSql =
                    "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";

            try (PreparedStatement ps =
                         con.prepareStatement(insertSql)) {

                ps.setString(1, "Wings of Fire");
                ps.setString(2, "A. P. J. Abdul Kalam");
                ps.setDouble(3, 250.0);

                int rows = ps.executeUpdate();

                System.out.println("\n" + rows + " row inserted");
            }


            // ==========================================
            // TASK 4: UPDATE BOOK PRICE
            // ==========================================

            String updateSql =
                    "UPDATE book SET price = ? WHERE book_id = ?";

            try (PreparedStatement ps =
                         con.prepareStatement(updateSql)) {

                ps.setDouble(1, 300.0);
                ps.setInt(2, 2);

                int rows = ps.executeUpdate();

                System.out.println(rows + " row updated");
            }


            // ==========================================
            // TASK 5: DELETE BOOK
            // ==========================================

            String deleteSql =
                    "DELETE FROM book WHERE book_id = ?";

            try (PreparedStatement ps =
                         con.prepareStatement(deleteSql)) {

                ps.setInt(1, 3);

                int rows = ps.executeUpdate();

                if (rows == 0) {
                    System.out.println("No book found.");
                } else {
                    System.out.println("Book deleted.");
                }
            }


            // ==========================================
            // FINAL DISPLAY
            // ==========================================

            System.out.println("\n--- Final Books ---");

            String finalSql = "SELECT * FROM book";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(finalSql)) {

                while (rs.next()) {

                    System.out.println(
                            rs.getInt("book_id") + " | " +
                                    rs.getString("title") + " | " +
                                    rs.getString("author") + " | " +
                                    rs.getDouble("price")
                    );
                }
            }


            // ==========================================
            // BONUS TASK
            // DISPLAY BOOKS WITH PRICE > 250
            // ==========================================

            String bonusSql =
                    "SELECT * FROM book WHERE price > ?";

            try (PreparedStatement ps =
                         con.prepareStatement(bonusSql)) {

                ps.setDouble(1, 250.0);

                try (ResultSet rs = ps.executeQuery()) {

                    System.out.println("\n--- Books Above 250 ---");

                    while (rs.next()) {

                        System.out.println(
                                rs.getInt("book_id") + " | " +
                                        rs.getString("title") + " | " +
                                        rs.getString("author") + " | " +
                                        rs.getDouble("price")
                        );
                    }
                }
            }


        } catch (SQLException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}