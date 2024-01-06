package tubes.pbo.maven.classes;

import tubes.pbo.maven.database.ConnectDatabase;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateHarga extends JFrame {
  private JTextField txtProductId;
  private JTextField txtNewPrice;
  private JButton btnUpdate;
  private ConnectDatabase databaseConnection;
  private CartSection cartSection;


  private DisplayMenuSection displayMenuSection;

  public UpdateHarga(JLabel jLabel, JTextField jTextField2, JTextField jTextField5, JButton jButton3, ConnectDatabase databaseConnection, CartSection cartSection) {
    // Periksa jika text field dan databaseConnection null
    if (jTextField2 == null || jTextField5 == null || databaseConnection == null || cartSection == null) {
      throw new IllegalArgumentException("Text fields, databaseConnection, dan cartSection tidak boleh null");
    }

    this.txtProductId = jTextField2;
    this.txtNewPrice = jTextField5;
    this.btnUpdate = jButton3;
    this.databaseConnection = databaseConnection;
    this.cartSection = cartSection;

  }

  public void updatePriceAction(JTable tableUpdate) {
    try {
      // Parse input menjadi integer
      int productId = Integer.parseInt(txtProductId.getText().trim());
      int newPrice = Integer.parseInt(txtNewPrice.getText().trim());

      // Memanggil fungsi update harga di database
      databaseConnection.updateMenuPrice(productId, newPrice);

      // Perbarui tampilan CartSection
//      System.out.println("Sebelum update");
//      displayMenuSection.displayMenu(tableUpdate);
//      System.out.println("sesudah update");

      JOptionPane.showMessageDialog(this, "Harga berhasil diperbarui.");
    } catch (NumberFormatException nfe) {
      JOptionPane.showMessageDialog(this, "Masukkan nomor yang valid.");
    } catch (Exception ex) {
      System.out.println( ex.getMessage());
    }
  }
}