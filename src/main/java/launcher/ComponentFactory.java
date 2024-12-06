package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySql;
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

public class ComponentFactory {
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final SellRepository sellRepository;
    private final SellService sellService;
    private static volatile ComponentFactory instance;

    public static ComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage) {
        if (instance == null) {
            synchronized (ComponentFactory.class) {
                if (instance == null) {
                    instance = new ComponentFactory(componentsForTest, primaryStage);
                }
            }
        }
        return instance;
    }

    public ComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySql(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        this.sellRepository = new SellRepositoryMySql(connection);
        this.sellService = new SellServiceImpl(sellRepository);
        List<BookDTO> books = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage, books);
        this.bookController = new BookController(bookView, bookService, sellService);
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
}
