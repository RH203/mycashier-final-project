package tubes.pbo.maven;

import tubes.pbo.maven.gui.CashierPage;

import javax.swing.SwingUtilities;
/**
 * mycashier_pbo2
 *
 */
public class App {
  public static void main( String[] args ) {
    /* Create and display the form */
    java.awt.EventQueue.invokeLater(() -> {
      new CashierPage().setVisible(true);
    });
  }
}
