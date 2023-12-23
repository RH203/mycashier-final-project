package tubes.pbo.maven.database;

import tubes.pbo.maven.classes.Menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectDatabase {
  static final String DB_URL = "jdbc:mysql://localhost:3306/kasir-pbo";
  static final String USER = "root"; // Isi dengan username database
  static final String PASS = "Raihanfirdaus20."; // Password MySQL jika ada

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL, USER, PASS);
  }

  // Get all data from database (DisplayMenuSection)
  public Menu[] getMenuData() {
    ArrayList<Menu> menuList = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      String query = "SELECT * FROM menu";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query);
           ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
          int id = resultSet.getInt("id_menu");
          String name = resultSet.getString("nama_menu");
          int price = resultSet.getInt("harga");
          String category = resultSet.getString("Kategori");

          // Create Menu object and add to the list
          Menu menu = new Menu(id, name, price, category);
          menuList.add(menu);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Convert ArrayList to an array
    return menuList.toArray(new Menu[0]);
  }
}
