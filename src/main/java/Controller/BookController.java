package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;
    public BookController(BookView bookView, BookService bookService) {
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();

            if(title.isEmpty() || author.isEmpty()){
                bookView.addDisplayAlertMessage("Save Error!","Problem at Author or Title fields!", "Can not have empty Title or Author field!");
            }else {
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).build();
                boolean saveBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                if(saveBook){
                    bookView.addDisplayAlertMessage("Save Successfu!","Book Added!","Book was added successfully to the database!");
                    bookView.addBookToObservableList(bookDTO);
                }else {
                    bookView.addDisplayAlertMessage("Save Error!","Problem at adding the Book!", "There was a problem at adding the book to the database pls try again!");
                }
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            BookDTO bookDTO = bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null){
                boolean deleteBook = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if(deleteBook){
                    bookView.addDisplayAlertMessage("Delete Successfu!","Book Deleted!","Book was deleted successfully from the database!");
                    bookView.removeBookFromObservableList(bookDTO);
                }else {
                    bookView.addDisplayAlertMessage("Delete Error!","Problem at deleting the Book!", "There was a problem at deleting the book from the database pls try again!");
                }
            }else {
                bookView.addDisplayAlertMessage("Delete Error!","Problem at deleting the Book!", "Please select a book to delete!");
            }
        }
    }
}
