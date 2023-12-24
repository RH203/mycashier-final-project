package tubes.pbo.maven.classes;

import tubes.pbo.maven.gui.CashierPage;
import tubes.pbo.maven.database.ConnectDatabase;

import javax.swing.*;
import java.sql.SQLException;

public class ButtonMenuSection {

  private CashierPage cashierPage;
  private CartSection cartSection;

  public ButtonMenuSection(CashierPage cashierPage, CartSection cartSection) {
    this.cashierPage = cashierPage;
    this.cartSection = cartSection;
  }

  public void handleMenuSubmit(int menuId, int jumlahMenu) {
    try {
      ConnectDatabase connectDatabase = new ConnectDatabase();
      Menu menu = connectDatabase.getMenuById(menuId);
      connectDatabase.sendGetMenuById(menuId, jumlahMenu);
      if (menu != null) {
        // Condition cek menu
        int idMenu = menu.getId();
        String menuName = menu.getName();
        double menuPrice = menu.getPrice();
        cartSection.addItem(idMenu, menuName, menuPrice, jumlahMenu);
        updateCartTextArea();
      } else {
        // Menu not found
//        System.out.println("Menu not found.");
        JOptionPane.showMessageDialog(null, "Menu tidak ada. (Cek kembali ID Menu!)");
      }
    } catch (SQLException e) {
      System.out.println("(ButtonMenuSection) ERROR: " + e.getMessage());
    }
  }

  public void handleAddMenu (int menuId, int jumlahMenu) {

  }

  public void handleDeleteMenu () {

  }

  private void updateCartTextArea() {
    StringBuilder cartText = new StringBuilder("CART:\n");
    for (CartSection.CartItem item : cartSection.getItemList()) {
      // Access CartItem menggunakan public method
      String itemName = item.getName();
      double itemPrice = item.getPrice();
      int itemQuantity = item.getQuantity();

      // Menampilkan hasil menggunaakn method append
      cartText.append(itemName).append(" x ").append(itemQuantity)
              .append(": Rp. ").append(itemPrice * itemQuantity).append("\n");
    }
    cashierPage.setCartTextArea(cartText.toString());
  }
}
