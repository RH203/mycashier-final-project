package tubes.pbo.maven.classes;

import tubes.pbo.maven.database.ConnectDatabase;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Update extends JFrame {
    private JTextField txtProductId;
    private JTextField txtNewPrice;
    private JButton btnUpdate;
    private ConnectDatabase databaseConnection;
    private CartSection cartSection; // Referensi ke CartSection

    // Modifikasi konstruktor untuk menerima CartSection
    public Update(JLabel jLabel, JTextField jTextField2, JTextField jTextField5, JButton jButton3, ConnectDatabase databaseConnection, CartSection cartSection) {
        // Periksa jika text field dan databaseConnection tidak null
        if (jTextField2 == null || jTextField5 == null || databaseConnection == null || cartSection == null) {
            throw new IllegalArgumentException("Text fields, databaseConnection, dan cartSection tidak boleh null");
        }

        this.txtProductId = jTextField2;
        this.txtNewPrice = jTextField5;
        this.btnUpdate = jButton3;
        this.databaseConnection = databaseConnection;
        this.cartSection = cartSection; // Simpan referensi CartSection

        // Atur teks untuk komponen jLabel dan jButton
        jLabel.setText("Update");
        jButton3.setText("Update");

        // Konfigurasi frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(jLabel.getText());
        setSize(400, 200);

        // Membuat dan mengatur panel
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        // Menambahkan komponen ke panel
       // panel.add(jLabel);
       // panel.add(txtProductId);
       // panel.add(txtNewPrice);
        // panel.add(btnUpdate);

        // Menambahkan action listener ke tombol btnUpdate
//        btnUpdate.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updatePriceAction();
//            }
//        });
//    }

    public void updatePriceAction() {
        try {
            // Parse input menjadi integer
            int productId = Integer.parseInt(txtProductId.getText().trim());
            int newPrice = Integer.parseInt(txtNewPrice.getText().trim());

            // Memanggil fungsi update harga di database
            databaseConnection.updateMenuPrice(productId, newPrice);

            // Perbarui tampilan CartSection
            cartSection.updateCartTextArea();

            JOptionPane.showMessageDialog(this, "Harga berhasil diperbarui.");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Masukkan nomor yang valid.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
