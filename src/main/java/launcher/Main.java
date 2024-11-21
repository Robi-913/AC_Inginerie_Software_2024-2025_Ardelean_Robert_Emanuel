package launcher;

import database.DatabaseConnectionFactory;
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

public class Main {
    public static void main(String[] args) {
        //Launcher.main(args);


//        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
//        BookRepository bookRepository = new BookRepositortCacheDecorator(new BookRepositoryMySql(connection), new Cache<>());
//        BookService bookService = new BookServiceImpl(bookRepository);
//
//        Book book = new BookBuilder().setTitle("Ion").setAuthor("Liviu Rebreanu").setPublishedDate(LocalDate.of(1910,10,20)).build();
//        bookRepository.removeAll();
//        bookService.save(book);
//        bookService.findAll().forEach(System.out::println);

        BookRepository bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySql(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()),
                new Cache<>()
        );

        BookService bookService = new BookServiceImpl(bookRepository);

        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();

        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);

        UserRepository userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);

        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);

        if(userRepository.existsByUsername("Buna")){
            System.out.println("This user exist");
        }else {
            authenticationService.register("Buna","suntuntest");

        }

        System.out.println(authenticationService.login("Buna","suntuntest"));

    }
}