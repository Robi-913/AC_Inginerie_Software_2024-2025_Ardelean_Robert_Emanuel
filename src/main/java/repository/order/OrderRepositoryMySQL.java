package repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryMySQL implements OrderRepository {

    private final Connection connection;

    public OrderRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getSalesReport(String startDate, String endDate) {
        String sql = "SELECT u.id AS user_id, u.username AS employee_email, " +
                "SUM(o.total_price) AS total_sales, " +
                "SUM(o.stock) AS total_stock_sold, " +
                "MIN(o.time) AS first_sale_date, " +
                "MAX(o.time) AS last_sale_date " +
                "FROM `order` o " +
                "JOIN user u ON o.user_id = u.id " +
                "WHERE o.time BETWEEN ? AND ? " +
                "GROUP BY u.id, u.username";

        List<String> report = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, startDate);
            statement.setString(2, endDate);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String employeeEmail = resultSet.getString("employee_email");
                double totalSales = resultSet.getDouble("total_sales");
                long totalStockSold = resultSet.getLong("total_stock_sold");
                String firstSaleDate = resultSet.getString("first_sale_date");
                String lastSaleDate = resultSet.getString("last_sale_date");

                report.add("ID: " + userId +
                        ", Email: " + employeeEmail +
                        ", Total Sales: $" + totalSales +
                        ", Total Stock Sold: " + totalStockSold +
                        ", First Sale Date: " + firstSaleDate +
                        ", Last Sale Date: " + lastSaleDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }
}
