package repository.sell;

import model.Book;


public interface SellRepository {
    boolean delete(String author, String title);

    boolean insert(Long employeeId, Double totalPrice, Long soldStock);

    boolean sell(Book book, Long soldStock, Long employeeId);
    boolean updateStock(String author, String title, Long newStock);
}
