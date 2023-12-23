package tubes.pbo.maven.classes;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import tubes.pbo.maven.classes.Menu;
import tubes.pbo.maven.database.ConnectDatabase;

public class DisplayMenuSection {

  private ConnectDatabase connectDatabase;

  public DisplayMenuSection(ConnectDatabase connectDatabase) {
    this.connectDatabase = connectDatabase;
  }

  // This function displays the menu in the specified table
  public void displayMenu(JTable table) {
    // Connect to the database and retrieve menu data
    Menu[] menuItems = connectDatabase.getMenuData();

    // Convert menu data to a format suitable for display
    Object[][] tableData = convertToTableData(menuItems);

    // Set the data in the table
    setTableData(table, tableData);
  }

  private Object[][] convertToTableData(Menu[] menuItems) {
    Object[][] data = new Object[menuItems.length][4]; 

    for (int i = 0; i < menuItems.length; i++) {
      data[i][0] = menuItems[i].getId();
      data[i][1] = menuItems[i].getName();
      data[i][2] = menuItems[i].getPrice();
      data[i][3] = menuItems[i].getDescription();

    }

    return data;
  }

  private void setTableData(JTable table, Object[][] data) {
    // Set the data in your table model
    DefaultTableModel model = new DefaultTableModel(data, new Object[]{"ID", "Menu", "Harga", "Kategori"});
    table.setModel(model);
  }
}
