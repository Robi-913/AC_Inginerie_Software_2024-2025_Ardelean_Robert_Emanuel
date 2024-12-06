package service.sell;

import model.Book;
import repository.sell.SellRepository;

public class SellServiceImpl implements SellService{
    private SellRepository sellRepository;

    public SellServiceImpl(SellRepository sellRepository) {
        this.sellRepository = sellRepository;
    }

    @Override
    public boolean sell(Book book, Long stock, Long employeeId) {
        sellRepository.sell(book, stock, employeeId);
        return true;
    }
}
