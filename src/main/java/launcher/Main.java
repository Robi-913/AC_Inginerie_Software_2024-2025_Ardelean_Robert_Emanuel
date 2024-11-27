package launcher;

import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySql;
import repository.book.Cache;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //Launcher.main(args);


        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySql(connection), new Cache<>());
        BookService bookService = new BookServiceImpl(bookRepository);

        bookService.findAll().forEach(System.out::println);
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

    }
}