package launcher;

import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySql;
import repository.book.Cache;
import repository.order.OrderRepository;
import repository.order.OrderRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //Launcher.main(args);


//        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
//        BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySql(connection), new Cache<>());
//        BookService bookService = new BookServiceImpl(bookRepository);
//
//        bookService.findAll().forEach(System.out::println);
//
//        BookRepository bookRepository = new BookRepositoryCacheDecorator(
//                new BookRepositoryMySql(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()),
//                new Cache<>()
//        );
//
//        BookService bookService = new BookServiceImpl(bookRepository);
//
//        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();
//
//        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
//
//        UserRepository userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
//
//        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
//
//        if(userRepository.existsByUsername("Buna")){
//            System.out.println("This user exist");
//        }else {
//            authenticationService.register("Buna","suntuntest");
//
//        }
//
//        System.out.println(authenticationService.login("Buna","suntuntest"));


        OrderRepository orderRepository = new OrderRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(false).getConnection());
        OrderService orderService = new OrderServiceImpl(orderRepository);

        // Generează raportul PDF
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";
        String outputFilePath = "sales_report.pdf";

        orderService.generateReport(startDate, endDate, outputFilePath);

        System.out.println("Report generated at: " + outputFilePath);

    }
}