package tubes.pbo.maven.classes;

// Import class
import tubes.pbo.maven.classes.*;
import tubes.pbo.maven.gui.CashierPage;
import tubes.pbo.maven.database.ConnectDatabase;

public class ButtonMenuSection {

  private CashierPage cashierPage;

  public ButtonMenuSection(CashierPage cashierPage) {
    this.cashierPage = cashierPage;
  }

  public void handleMenuSubmit(int menuId) {
    ConnectDatabase connectDatabase = new ConnectDatabase();
    Menu menu = connectDatabase.getMenuById(menuId);

    if (menu != null) {
      // Menu exists, proceed to CartSection
      cartSection.addToCart(menu);
    } else {
      // Menu not found
      System.out.println("");
    }
  }

  private void onTambahMenuButtonClicked() {
    int menuId = cashierPage.getSelectedMenuId();

    ConnectDatabase connectDatabase = new ConnectDatabase();
    Menu menu = connectDatabase.getMenuById(id_menu);

    if (menu != null) {
      cashierPage.appendToCartTextArea(menu.toString() + "\n");
    } else {
      cashierPage.appendToCartTextArea("Menu with ID " + menuId + " not found.\n");
    }
  }
}
