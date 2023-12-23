package tubes.pbo.maven.classes;

import tubes.pbo.maven.gui.CashierPage;
import tubes.pbo.maven.database.ConnectDatabase;

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

      if (menu != null) {
        // Menu exists, proceed to CartSection
        String menuName = menu.getName();
        double menuPrice = menu.getPrice();
        cartSection.addItem(menuName, menuPrice, jumlahMenu);
        updateCartTextArea();
      } else {
        // Menu not found
        System.out.println("Menu not found.");
      }
    } catch (SQLException e) {
      System.out.println("(ButtonMenuSection) ERROR: " + e.getMessage());
    }
  }

  private void updateCartTextArea() {
    StringBuilder cartText = new StringBuilder("CART:\n");
    for (CartSection.CartItem item : cartSection.getItemList()) {
      // Access CartItem fields using public accessor methods
      String itemName = item.getName();
      double itemPrice = item.getPrice();
      int itemQuantity = item.getQuantity();

      // Use these fields as needed
      cartText.append(itemName).append(" x ").append(itemQuantity)
              .append(": Rp. ").append(itemPrice * itemQuantity).append("\n");
    }
    cashierPage.setCartTextArea(cartText.toString());
  }
}
