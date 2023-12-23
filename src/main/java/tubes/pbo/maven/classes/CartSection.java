package tubes.pbo.maven.classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartSection {
  private int itemCount;
  private JLabel itemCountLabel;

  public void placeComponents(JFrame frame) {
    JPanel cartPanel = createCartPanel();

    frame.add(cartPanel, BorderLayout.NORTH);
  }

  private JPanel createCartPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    itemCountLabel = new JLabel("Jumlah Item: 0");

    JButton incrementButton = new JButton("Increment");
    JButton decrementButton = new JButton("Decrement");
    JButton confirmButton = new JButton("Konfirmasi");

    incrementButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        incrementItemCount();
      }
    });

    decrementButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        decrementItemCount();
      }
    });

    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        confirmCart();
      }
    });

    panel.add(incrementButton);
    panel.add(decrementButton);
    panel.add(confirmButton);
    panel.add(itemCountLabel);

    return panel;
  }

  public void addToCart(int menuId) {
    itemCount += menuId;
    updateItemCountLabel();
  }

  private void incrementItemCount() {
    itemCount++;
    updateItemCountLabel();
  }

  private void decrementItemCount() {
    if (itemCount > 0) {
      itemCount--;
      updateItemCountLabel();
    }
  }

  private void confirmCart() {
    System.out.println("Keranjang dikonfirmasi dengan " + itemCount + " item.");
  }

  private void updateItemCountLabel() {
    itemCountLabel.setText("Jumlah Item: " + itemCount);
  }
}