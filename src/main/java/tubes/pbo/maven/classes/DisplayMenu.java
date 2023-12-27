package tubes.pbo.maven.classes;

import tubes.pbo.maven.database.ConnectDatabase;
import tubes.pbo.maven.gui.CashierPage;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DisplayMenu {

    private void displayMenu(String category) {
        String query = "SELECT nama_menu, harga FROM menu WHERE kategori_id = (SELECT id FROM kategori WHERE nama = ?)";

        try (Connection connection = ConnectDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Daftar Menu " + category + ":");
                while (resultSet.next()) {
                    String nama = resultSet.getString("nama");
                    int harga = resultSet.getInt("harga");
                    System.out.println(nama + "\t\tRp " + harga);
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private ConnectDatabase connectDatabase;


    private boolean isValidCategory(String selectedCategory, String[] categories) {
        for (String category : categories) {
            if (category.equals(selectedCategory)) {
                return true;
            }
        }
        return false;
    }

    private String[] getDistinctCategories() {
        Set<String> categorySet = new HashSet<>();

        try (Connection connection = ConnectDatabase.getConnection()) {
            String query = "SELECT DISTINCT nama FROM kategori";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String category = resultSet.getString("nama");
                    categorySet.add(category);
                }
            }
        } catch (SQLException e) {
            System.out.println("(Get Distinct Categories) Error: " + e.getMessage());
        }

        return categorySet.toArray(new String[0]);
    }

    private Object[][] convertToTableData(Menu[] menuItems) {
        Object[][] data = new Object[menuItems.length][4];

        for (int i = 0; i < menuItems.length; i++) {
            data[i][0] = menuItems[i].getId();
            data[i][1] = menuItems[i].getName();
            data[i][2] = menuItems[i].getPrice();
            data[i][3] = menuItems[i].getCategory();
        }

        return data;
    }

    private void setTableData(JTable table, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, new Object[]{"ID", "Menu", "Harga", "Kategori"});
        table.setModel(model);
    }

}

