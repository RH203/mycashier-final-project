package tubes.pbo.maven.classes;

import tubes.pbo.maven.gui.CashierPage;

import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.List;

public class CartSection {

  private JTextArea cartTextArea;
  private List<CartItem> cartItems; // Semua input disimpan disini

  public CartSection(JTextArea cartTextArea, CashierPage cashierPage) {
    this.cartTextArea = cartTextArea;
    this.cartItems = new ArrayList<>();
  }

  // Add a new item to the cart
  public void addItem(int idMenu, String menuName, double menuPrice, int quantity) {
    CartItem newItem = new CartItem(menuName, menuPrice, quantity, idMenu);
    cartItems.add(newItem);
    updateCartTextArea();
  }

  // Remove an item from the cart
  public void removeItem(int idMenu) {
    if (idMenu >= 0 && idMenu < cartItems.size()) {
      cartItems.remove(idMenu);
      updateCartTextArea();
    }
  }

  // Update cartTextArea with the new menu information
  public void updateCartTextArea() {
    StringBuilder cartText = new StringBuilder("CART:\n");
    for (CartItem item : cartItems) {
      // Access CartItem fields directly
      int itemId = item.getIdMenu();
      String itemName = item.getName();
      double itemPrice = item.getPrice();
      int itemQuantity = item.getQuantity();

      // Use these fields as needed
      cartText.append(itemName).append(" x ").append(itemQuantity)
              .append(": Rp").append(itemPrice * itemQuantity).append("\n");
//      (DEBUG OUTPUT BUTTON MENU)
//      System.out.println("( " + itemId + " )" + itemName + " x " + itemQuantity + ": $" + (itemPrice * itemQuantity));
    }
    cartTextArea.setText(cartText.toString());
  }
  public List<CartItem> getCartItems() {
    return cartItems;
  }

  public void clearCart () {
    cartItems.clear();
    updateCartTextArea();
  }

  // Accessor method to get the list of CartItems
  List<CartItem> getItemList() {
    return cartItems;
  }

  public static class CartItem {
    private String name;
    private double price;
    private int quantity;
    private int idMenu;

    public CartItem(String name, double price, int quantity, int idMenu) {
      this.name = name;
      this.price = price;
      this.quantity = quantity;
      this.idMenu = idMenu;
    }

    public String getName() {
      return name;
    }

    public double getPrice() {
      return price;
    }

    public int getQuantity() {
      return quantity;
    }
    public int getIdMenu ()  { return idMenu; }
    public void setQuantity (int newQuantity) {
      this.quantity = newQuantity;
    }
  }
}
