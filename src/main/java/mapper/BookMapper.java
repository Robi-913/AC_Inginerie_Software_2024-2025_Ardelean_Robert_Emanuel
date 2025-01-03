package mapper;

import model.Book;
import model.builder.BookBuilder;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDTO convertBookToBookDTO(Book book) {
        return new BookDTOBuilder().setTitle(book.getTitle()).setAuthor(book.getAuthor()).setPrice(String.valueOf(book.getPrice())).setStock(String.valueOf(book.getStock())).setPublishedDate(String.valueOf(book.getPublishedDate())).build();
    }

    public static Book convertBookDTOToBook(BookDTO bookDTO) {
        return new BookBuilder().setTitle(bookDTO.getTitle()).setAuthor(bookDTO.getAuthor()).setPublishedDate(LocalDate.parse(bookDTO.getPublishedDate())).setPrice(Double.parseDouble(bookDTO.getPrice())).setStock(Long.parseLong(bookDTO.getStock())).build();
    }

    public static List<BookDTO> convertBookListToBookDTOList(List<Book> books) {
        return books.parallelStream().map(BookMapper::convertBookToBookDTO).collect(Collectors.toList());
    }

    public static List<Book> convertBookDTOListToBookList(List<BookDTO> bookDTOS) {
        return bookDTOS.parallelStream().map(BookMapper::convertBookDTOToBook).collect(Collectors.toList());
    }
}