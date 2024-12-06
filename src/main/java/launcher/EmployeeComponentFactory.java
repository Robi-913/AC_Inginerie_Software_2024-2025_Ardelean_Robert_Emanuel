package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySql;
import repository.book.Cache;
import repository.sell.SellRepository;
import repository.sell.SellRepositoryMySql;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.sell.SellService;
import service.sell.SellServiceImpl;
import view.BookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeComponentFactory {

    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final SellRepository sellRepository;
    private final SellService sellService;
    private final BookService bookService;
    private static EmployeeComponentFactory instance;

    public static EmployeeComponentFactory getInstance(Boolean componentsForTest, Stage stage){
        if (instance == null){
            instance = new EmployeeComponentFactory(componentsForTest, stage);
        }
        return instance;
    }

    public EmployeeComponentFactory(Boolean componentsForTest, Stage stage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySql(connection), new Cache<>());
        this.bookService = new BookServiceImpl(bookRepository);
        this.sellRepository =  new SellRepositoryMySql(connection);
        this.sellService = new SellServiceImpl(sellRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());
        this.bookView = new BookView(stage, bookDTOs);
        this.bookController = new BookController(bookView, bookService,sellService);

    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public SellRepository getSellRepository() {
        return sellRepository;
    }

    public SellService getSellService() {
        return sellService;
    }

    public static EmployeeComponentFactory getInstance() {
        return instance;
    }
}