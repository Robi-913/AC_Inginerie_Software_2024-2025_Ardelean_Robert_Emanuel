package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.session.UserSession;
import service.book.BookService;
import service.sell.SellService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDate;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;
    private final SellService sellService;

    public BookController(BookView bookView, BookService bookService, SellService sellService) {
        this.bookView = bookView;
        this.bookService = bookService;
        this.sellService = sellService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellerButtonListener(new SellerButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String stock = bookView.getStock();
            String price = bookView.getPrice();

            if (title.isEmpty() || author.isEmpty() || stock.isEmpty() || price.isEmpty()) {
                bookView.addDisplayAlertMessage(
                        "Save Error!",
                        "Missing Fields",
                        "Title, Author, Stock, and Price must not be empty!"
                );
            } else {
                try {
                    BookDTO bookDTO = new BookDTOBuilder()
                            .setTitle(title)
                            .setAuthor(author)
                            .setPrice(price)
                            .setStock(stock)
                            .setPublishedDate(String.valueOf(LocalDate.now()))
                            .build();

                    boolean saveBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                    if (saveBook) {
                        bookView.addDisplayAlertMessage(
                                "Save Successful!",
                                "Book Added!",
                                "Book was added successfully to the database!"
                        );
                        bookView.addBookToObservableList(bookDTO);
                    } else {
                        bookView.addDisplayAlertMessage(
                                "Save Error!",
                                "Database Error",
                                "There was a problem adding the book to the database. Please try again."
                        );
                    }
                } catch (NumberFormatException e) {
                    bookView.addDisplayAlertMessage(
                            "Save Error!",
                            "Invalid Input",
                            "Price and Stock must be valid numbers."
                    );
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
                boolean deleteBook = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deleteBook) {
                    bookView.addDisplayAlertMessage(
                            "Delete Successful!",
                            "Book Deleted!",
                            "Book was deleted successfully from the database!"
                    );
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.addDisplayAlertMessage(
                            "Delete Error!",
                            "Database Error",
                            "There was a problem deleting the book. Please try again."
                    );
                }
            } else {
                bookView.addDisplayAlertMessage(
                        "Delete Error!",
                        "No Book Selected",
                        "Please select a book to delete."
                );
            }
        }
    }

    private class SellerButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = bookView.getBookTableView().getSelectionModel().getSelectedItem();
            String stock = bookView.getSellStock();
            Long userId = UserSession.getInstance().getUserId();

            System.out.println(userId);
            System.out.println(stock);

            if (bookDTO == null) {
                bookView.addDisplayAlertMessage(
                        "Sell Error!",
                        "No Book Selected",
                        "Please select a book to sell."
                );
                return;
            }

            if (stock.isEmpty() || userId == null) {
                bookView.addDisplayAlertMessage(
                        "Sell Error!",
                        "Missing Fields",
                        "Stock to sell or user ID must not be empty."
                );
                return;
            }

            try {
                Long soldStock = Long.parseLong(stock);
                Long currentStock = Long.parseLong(bookDTO.getStock());

                if (soldStock > currentStock) {
                    bookView.addDisplayAlertMessage(
                            "Sell Error!",
                            "Insufficient Stock",
                            "You are trying to sell more books than are available in stock."
                    );
                    return;
                }

                boolean sellBook = sellService.sell(BookMapper.convertBookDTOToBook(bookDTO), soldStock, userId);
                if (sellBook) {
                    long newStock = currentStock - soldStock;

                    if (newStock == 0) {
                        // Dacă stocul ajunge la 0, eliminăm cartea din listă
                        bookView.removeBookFromObservableList(bookDTO);
                    } else {
                        // Dacă stocul nu este 0, actualizăm lista
                        bookDTO.setStock(Long.toString(newStock));
                        bookView.updateBookInObservableList(bookDTO);
                    }

                    bookView.addDisplayAlertMessage(
                            "Sell Successful!",
                            "Book Sold!",
                            "The book was successfully sold and the stock has been updated!"
                    );
                } else {
                    bookView.addDisplayAlertMessage(
                            "Sell Error!",
                            "Database Error",
                            "There was a problem completing the sale. Please try again."
                    );
                }
            } catch (NumberFormatException e) {
                bookView.addDisplayAlertMessage(
                        "Sell Error!",
                        "Invalid Input",
                        "Stock to sell must be a valid number."
                );
            }
        }
    }

}
