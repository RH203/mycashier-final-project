import tubes.pbo.maven.database.ConnectDatabase;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Update extends JFrame {
    private JTextField txtProductId;
    private JTextField txtNewPrice;
    private JButton btnUpdate;
    private ConnectDatabase databaseConnection; // Deklarasi variabel untuk ConnectDatabase

    public Update() {
        // Inisialisasi koneksi database
        databaseConnection = new ConnectDatabase();

        // Membuat dan mengatur JFrame
        setTitle("Update Harga Produk");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Membuat dan menambahkan komponen
        txtProductId = new JTextField();
        txtProductId.setBounds(10, 10, 150, 20);
        add(txtProductId);

        txtNewPrice = new JTextField();
        txtNewPrice.setBounds(10, 40, 150, 20);
        add(txtNewPrice);

        btnUpdate = new JButton("Update Harga");
        btnUpdate.setBounds(10, 70, 150, 20);
        add(btnUpdate);

        // Tambahkan action listener ke tombol
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePriceAction();
            }
        });
    }

    private void updatePriceAction() {
        try {
            // Mengambil nilai dari text fields
            int productId = Integer.parseInt(txtProductId.getText());
            int newPrice = Integer.parseInt(txtNewPrice.getText()); // Konversi ke integer jika harga adalah tipe integer

            // Memanggil fungsi update harga
            databaseConnection.updateMenuPrice(productId, newPrice); // Menggunakan metode yang benar
            JOptionPane.showMessageDialog(this, "Harga berhasil diperbarui.");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Masukkan nomor yang valid.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Update frame = new Update();
        frame.setVisible(true);
    }
}
