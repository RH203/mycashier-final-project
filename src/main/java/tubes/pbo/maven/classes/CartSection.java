package tubes.pbo.maven.classes;

import tubes.pbo.maven.gui.CashierPage;

import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.List;

public class CartSection {

  private JTextArea cartTextArea;
  private List<CartItem> cartItems;

  public CartSection(JTextArea cartTextArea, CashierPage cashierPage) {
    this.cartTextArea = cartTextArea;
    this.cartItems = new ArrayList<>();
  }

  // Add a new item to the cart
  public void addItem(String menuName, double menuPrice, int quantity) {
    CartItem newItem = new CartItem(menuName, menuPrice, quantity);
    cartItems.add(newItem);
    updateCartTextArea();
  }

  // Remove an item from the cart
  public void removeItem(int index) {
    if (index >= 0 && index < cartItems.size()) {
      cartItems.remove(index);
      updateCartTextArea();
    }
  }

  // Update cartTextArea with the new menu information
  private void updateCartTextArea() {
    StringBuilder cartText = new StringBuilder("CART:\n");
    for (CartItem item : cartItems) {
      // Access CartItem fields directly
      String itemName = item.getName();
      double itemPrice = item.getPrice();
      int itemQuantity = item.getQuantity();

      // Use these fields as needed
      cartText.append(itemName).append(" x ").append(itemQuantity)
              .append(": $").append(itemPrice * itemQuantity).append("\n");
    }
    cartTextArea.setText(cartText.toString());
  }

//  public List<CartItem> getCartItems() {
//    return cartItems;
//  }

  // Accessor method to get the list of CartItems
  List<CartItem> getItemList() {
    return cartItems;
  }

  public static class CartItem {
    private String name;
    private double price;
    private int quantity;

    public CartItem(String name, double price, int quantity) {
      this.name = name;
      this.price = price;
      this.quantity = quantity;
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
  }
}
