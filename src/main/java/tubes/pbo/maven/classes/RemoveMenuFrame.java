package tubes.pbo.maven.classes;

import tubes.pbo.maven.gui.CashierPage;
import tubes.pbo.maven.classes.CartSection;
import javax.swing.*;
import java.awt.event.ActionEvent;


public class RemoveMenuFrame extends JFrame {
  private CashierPage cashierPage;
  private CartSection cartSection;
  private JPanel panel;
  private JTextField idMenuFieldRemove, quantityFieldRemove;
  private JButton btnRemove;

  public RemoveMenuFrame (CashierPage cashierPage, CartSection cartSection) {
    this.cashierPage = cashierPage;
    this.cartSection = cartSection;
    initComponents();
  }

  public void initComponents () {
    this.setTitle("Remove Item");
    this.setSize(350, 350);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    panel = new JPanel();
    this.add(panel);

    idMenuFieldRemove = new JTextField(20);
    panel.add(new JLabel("ID Menu: "));
    panel.add(idMenuFieldRemove);

    quantityFieldRemove = new JTextField(20);
    panel.add(new JLabel("Quantity: "));
    panel.add(quantityFieldRemove);

    btnRemove = new JButton("Submit");
    panel.add(btnRemove);

    btnRemove.addActionListener(this::removeMenu);
  }

  private void removeMenu (ActionEvent evt) {
    try{
      int idMenu = Integer.parseInt(idMenuFieldRemove.getText());
      int quantity = Integer.parseInt(quantityFieldRemove.getText());
      boolean found = false;

      for (int i = 0; i < cartSection.getCartItems().size(); i++) {

        if (idMenu == cartSection.getCartItems().get(i).getIdMenu()) {
          int tempQuantityRemove = cartSection.getCartItems().get(i).getQuantity();

          if (quantity >= tempQuantityRemove) {
            cartSection.removeItem(i);
            cartSection.updateCartTextArea();
          } else if (quantity < tempQuantityRemove) {
            cartSection.getCartItems().get(i).setQuantity(tempQuantityRemove - quantity);
            cartSection.updateCartTextArea();
          } else {
            cartSection.getCartItems().get(i).setQuantity(tempQuantityRemove);
            cartSection.updateCartTextArea();
          }
          found = true;
          break;
        }
      }

      if (!found) {
        JOptionPane.showMessageDialog(null, "ID menu tidak terdapat dikeranjang. ");
//        System.out.println("Condition found: gajalan?"); Debug kondisi tidak muncul
      }

      dispose();
//      cartSection.updateCartTextArea();
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "ID menu atau quantity harus berupa angka!");
      System.out.println("Error function removeMenu (RemoveMenuFrame) " + e.getMessage());
    }
  }
}
