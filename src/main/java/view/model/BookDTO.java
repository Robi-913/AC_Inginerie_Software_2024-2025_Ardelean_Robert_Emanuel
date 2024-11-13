package view.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class BookDTO {
    private StringProperty author;
    private StringProperty title;

    public void setAuthor(String author) {
        authorPropert().set(author);
    }

    private StringProperty authorPropert() {
        if(author == null) {
            author = new SimpleStringProperty(this, "author");
        }
        return author;
    }

    public String getAuthor() {
        return authorPropert().get();
    }

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    private StringProperty titleProperty() {
        if(title == null) {
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }

    public String getTitle() {
        return titleProperty().get();
    }
}
