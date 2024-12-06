package service.sell;

import model.Book;

public interface SellService {
    boolean sell(Book book, Long stock, Long employeeId);
}

