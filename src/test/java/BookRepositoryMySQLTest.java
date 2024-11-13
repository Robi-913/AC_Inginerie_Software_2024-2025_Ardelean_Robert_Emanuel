import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BookRepositoryMySql;

import java.sql.Connection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMySQLTest {
    public static BookRepositoryMySql bookRepositoryMySql;

    @BeforeAll
    public static void setupDatabase(){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();
        bookRepositoryMySql = new BookRepositoryMySql(connection);
    }

    @BeforeEach
    public void clearDatabase(){
       bookRepositoryMySql.removeAll();
    }

    @Test
    public void testFindAll() {
        List<Book> books = bookRepositoryMySql.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void testFindById() {
        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1900, 10, 2))
                .build();
        bookRepositoryMySql.save(book);

        Optional<Book> foundBook = bookRepositoryMySql.findById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals("Ion", foundBook.get().getTitle(), "Titlul cartii ar trebui să fie 'Ion'");
    }

    @Test
    public void testSave() {
        Book book = new BookBuilder()
                .setTitle("Baltagul")
                .setAuthor("Mihail Sadoveanu")
                .setPublishedDate(LocalDate.of(1930, 5, 15))
                .build();

        boolean isSaved = bookRepositoryMySql.save(book);
        assertTrue(isSaved, "Cartea ar trebui sa fie salvata cu succes.");

        List<Book> books = bookRepositoryMySql.findAll();
        assertEquals(1, books.size(), "Ar trebui sa existe o singura carte in baza de date.");
    }

    @Test
    public void testDelete() {
        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1900, 10, 2))
                .build();
        bookRepositoryMySql.save(book);

        boolean isDeleted = bookRepositoryMySql.delete(book);
        assertTrue(isDeleted, "Cartea ar trebui sa fie satearsa.");

        List<Book> books = bookRepositoryMySql.findAll();
        assertEquals(0, books.size(), "Baza de date ar trebui să fie goala dupa stergere.");
    }

    @Test
    public void testRemoveAll() {
        bookRepositoryMySql.save(new BookBuilder().setTitle("Ion").setAuthor("Liviu Rebreanu").setPublishedDate(LocalDate.of(1900, 10, 2)).build());
        bookRepositoryMySql.save(new BookBuilder().setTitle("Baltagul").setAuthor("Mihail Sadoveanu").setPublishedDate(LocalDate.of(1930, 5, 15)).build());

        bookRepositoryMySql.removeAll();

        List<Book> books = bookRepositoryMySql.findAll();
        assertEquals(0, books.size(), "Baza de date ar trebui sa fie goala dupa stergerea tuturor inregistrarilor.");
    }
}

