package tubes.pbo.maven.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

    public static void updatePrice(Connection connection, String id, double price) throws SQLException {
        try {
            // Buat statement SQL untuk update harga
            PreparedStatement statement = connection.prepareStatement("UPDATE menu SET price = ? WHERE id = ?");

            // Ganti nilai ? dengan harga baru
            statement.setDouble(1, price);
            statement.setString(2, id);

            // Eksekusi statement SQL
            int rowsAffected = statement.executeUpdate();

            // Cek apakah update berhasil
            if (rowsAffected == 1) {
                System.out.println("Harga makanan atau minuman berhasil diupdate");
            } else {
                System.out.println("Harga makanan atau minuman gagal diupdate");
            }
        } catch (SQLException e) {
            System.out.println("Error saat mengupdate harga: " + e.getMessage());
            // Lakukan rollback atau tindakan lain untuk mengatasi error
        } finally {
            // Tutup koneksi database
            if (connection != null) {
                connection.close();
            }
        }
    }
}


