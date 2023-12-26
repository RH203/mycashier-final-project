package tubes.pbo.maven.classes;

import tubes.pbo.maven.database.ConnectDatabase;
import tubes.pbo.maven.gui.CashierPage;
import tubes.pbo.maven.classes.CartSection;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddMenuFrame extends JFrame {
  private CashierPage cashierPage;
  private CartSection cartSection;
  private ConnectDatabase connectDatabase;

  private JPanel panel;
  private JTextField idMenuField, jumlahMenuField;
  private JButton addButtonMenuFrame;

  public AddMenuFrame(CashierPage cashierPage, CartSection cartSection) {
    this.cashierPage = cashierPage;
    this.connectDatabase = new ConnectDatabase();
    this.cartSection = cartSection;
    initComponents();
  }

  public void initComponents() {
    this.setTitle("Add Menu");
    this.setSize(350, 200);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    panel = new JPanel();
    this.add(panel);

    idMenuField = new JTextField(20);
    panel.add(new JLabel("ID Menu: "));
    panel.add(idMenuField);

    jumlahMenuField = new JTextField(20);
    panel.add(new JLabel("Quantity: "));
    panel.add(jumlahMenuField);

    addButtonMenuFrame = new JButton("Submit");
    panel.add(addButtonMenuFrame);

    addButtonMenuFrame.addActionListener(this::addButtonActionPerformed);
  }

  private void addButtonActionPerformed(ActionEvent evt) {
    try {
      int idMenu = Integer.parseInt(idMenuField.getText());
      int newQuantity = Integer.parseInt(jumlahMenuField.getText());
      boolean foundIdMenu = false;

      for (int i = 0; i < cartSection.getCartItems().size(); i++) {
        if(idMenu == cartSection.getCartItems().get(i).getIdMenu()) {
          int tempNewQuantity = newQuantity + cartSection.getCartItems().get(i).getQuantity();
          cartSection.getCartItems().get(i).setQuantity(tempNewQuantity);
          foundIdMenu = true;
          break;
        }
      }

      if (!foundIdMenu) {
        JOptionPane.showMessageDialog(null, "ID menu tidak terdapat dikeranjang.");
      }

      connectDatabase.sendTambahQuanttiy(idMenu, newQuantity);
      dispose();
      cartSection.updateCartTextArea();
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "ID menu atau quantity harus berupa angka!");
      System.out.println("Error function addButtonActionPerformed (AddMenuFrame) " + e.getMessage());
    }
  }
}