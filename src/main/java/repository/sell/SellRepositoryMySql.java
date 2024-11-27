package repository.sell;

import model.Book;

import java.sql.*;
import java.time.LocalDateTime;

public class SellRepositoryMySql implements SellRepository {
    private final Connection connection;

    public SellRepositoryMySql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean delete(String author, String title) {
        String deleteSql = "DELETE FROM book WHERE author = ? AND title = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setString(1, author);
            deleteStatement.setString(2, title);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting book", e);
        }
    }

    @Override
    public boolean updateStock(String author, String title, Long newStock) {
        String updateStockSql = "UPDATE book SET stock = ? WHERE author = ? AND title = ?";
        try (PreparedStatement updateStockStatement = connection.prepareStatement(updateStockSql)) {
            updateStockStatement.setLong(1, newStock);
            updateStockStatement.setString(2, author);
            updateStockStatement.setString(3, title);
            return updateStockStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating book stock", e);
        }
    }

    @Override
    public boolean insert(Long employeeId, Double totalPrice) {
        String insertOrderSql = "INSERT INTO `order` (user_id, total_price, time) VALUES (?, ?, ?)";
        try (PreparedStatement insertOrderStatement = connection.prepareStatement(insertOrderSql)) {
            insertOrderStatement.setLong(1, employeeId);
            insertOrderStatement.setDouble(2, totalPrice);
            insertOrderStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            return insertOrderStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting order", e);
        }
    }

    @Override
    public boolean sell(Book book, Long soldStock, Long employeeId) {
        try {
            String getStockSql = "SELECT stock FROM book WHERE author = ? AND title = ?";
            try (PreparedStatement getStockStatement = connection.prepareStatement(getStockSql)) {
                getStockStatement.setString(1, book.getAuthor());
                getStockStatement.setString(2, book.getTitle());
                try (ResultSet resultSet = getStockStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Long currentStock = resultSet.getLong("stock");

                        if (currentStock >= soldStock) {
                            Long newStock = currentStock - soldStock;

                            if (newStock > 0) {
                                updateStock(book.getAuthor(), book.getTitle(), newStock);
                            } else {
                                delete(book.getAuthor(), book.getTitle());
                            }

                            double totalPrice = book.getPrice() * soldStock;
                            return insert(employeeId, totalPrice);
                        } else {
                            throw new RuntimeException("Not enough stock to complete the sale.");
                        }
                    } else {
                        throw new RuntimeException("Book not found in the database.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while selling book", e);
        }
    }
}
