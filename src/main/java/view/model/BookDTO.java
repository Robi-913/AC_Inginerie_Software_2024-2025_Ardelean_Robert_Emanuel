package view.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class BookDTO {
    private StringProperty author;
    private StringProperty title;
    private StringProperty price;
    private StringProperty stock;
    private StringProperty publishedDate;



    private StringProperty authorPropert() {
        if (author == null) {
            author = new SimpleStringProperty(this, "author");
        }
        return author;
    }

    public void setAuthor(String author) {
        authorPropert().set(author);
    }

    public String getAuthor() {
        return authorPropert().get();
    }

    private StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    private StringProperty priceProperty() {
        if (price == null) {
            price = new SimpleStringProperty(this, "price");
        }
        return price;
    }

    public void setPrice(String price) {
        priceProperty().set(price);
    }

    public String getPrice() {
        return priceProperty().get();
    }

    private StringProperty stockProperty() {
        if (stock == null) {
            stock = new SimpleStringProperty(this, "stock");
        }
        return stock;
    }

    public void setStock(String stock) {
        stockProperty().set(stock);
    }

    public String getStock() {
        return stockProperty().get();
    }

    public StringProperty getPublishedDateProperty() {
        if (publishedDate == null) {
            publishedDate = new SimpleStringProperty(this, "publishedDate");
        }
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        getPublishedDateProperty().set(publishedDate);
    }

    public String getPublishedDate() {
        return getPublishedDateProperty().get();
    }

}
