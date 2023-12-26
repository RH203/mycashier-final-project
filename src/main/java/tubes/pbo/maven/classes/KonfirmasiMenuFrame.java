package tubes.pbo.maven.classes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import tubes.pbo.maven.classes.CartSection;
import tubes.pbo.maven.database.ConnectDatabase;
import tubes.pbo.maven.gui.CashierPage;

public class KonfirmasiMenuFrame extends JFrame {

  private ConnectDatabase connectDatabase;
  private JTextField uangTextField;
  private JButton konfirmasiButton;
  private JLabel nilaiTotalHargaLabel;
  private CartSection cartSection;

  private int totalHarga;

  public KonfirmasiMenuFrame(CashierPage cashierPage) {
    connectDatabase = new ConnectDatabase();
    cartSection = new CartSection(cashierPage.getCartTextArea(), cashierPage);
    initComponents();
  }

  private void initComponents() {
    setTitle("Konfirmasi Pembayaran");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 200);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    JLabel uangLabel = new JLabel("Masukkan Jumlah Uang:");
    mainPanel.add(uangLabel);

    uangTextField = new JTextField(20);
    mainPanel.add(uangTextField);

    JLabel totalHargaLabel = new JLabel("Total Harga:");
    mainPanel.add(totalHargaLabel);

    nilaiTotalHargaLabel = new JLabel();
    mainPanel.add(nilaiTotalHargaLabel);

    konfirmasiButton = new JButton("Konfirmasi Pembayaran");
    konfirmasiButton.addActionListener(this::handleKonfirmasiButtonClick);
    mainPanel.add(konfirmasiButton);

    add(mainPanel);

    displayTotalHarga();
  }

  private void displayTotalHarga() {
    nilaiTotalHargaLabel.setText("Rp " + totalHarga);
  }

  public void setTotalHarga(int idPembayaran) {
    this.totalHarga = connectDatabase.sendTotalHarga();
    displayTotalHarga();
  }

  private void handleKonfirmasiButtonClick(ActionEvent event) {
    try {
      double jumlahUang = Double.parseDouble(uangTextField.getText());
      double kembalian = jumlahUang - totalHarga;

      JOptionPane.showMessageDialog(this, "Kembalian: Rp " + kembalian);
      dispose();
      cartSection.clearCart();
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Masukkan jumlah uang dengan benar.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

}
