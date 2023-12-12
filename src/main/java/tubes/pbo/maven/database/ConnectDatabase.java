package tubes.pbo.maven.database;

import java.sql.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectDatabase {
  static final String DB_URL = "jdbc:mysql://localhost:3306/mycashier-pbo";
  static final String USER = "root";
  static final String PASS = ""; // Password MySQL jika ada

  public static Connection getConnection () throws SQLException { return DriverManager.getConnection(DB_URL, USER, PASS); }

  // Get all data from databse (DisplayMenuSection)
  public List<Map<String, Object>> getAllData() throws SQLException {
    List<Map<String, Object>> dataList = new ArrayList<>();

    try (Connection conn = getConnection()) {
      String query = "SELECT * FROM produk";
      try (PreparedStatement stmt = conn.prepareStatement(query)) {
        try (ResultSet resultSet = stmt.executeQuery()) {
          ResultSetMetaData metaData = resultSet.getMetaData();
          int columnCount = metaData.getColumnCount();

          while (resultSet.next()) {
            Map<String, Object> rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
              rowData.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            dataList.add(rowData);
          }
        }
      }
    }

    return dataList;
  }



}
