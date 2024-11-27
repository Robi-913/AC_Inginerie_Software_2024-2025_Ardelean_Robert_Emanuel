package repository.sell;

import model.Book;

import java.sql.SQLException;

public interface SellRepository {
    boolean delete(String author, String title);

    boolean insert(Long employeeId, Double totalPrice);

    boolean sell(Book book, Long soldStock, Long employeeId);
    boolean updateStock(String author, String title, Long newStock);
}
