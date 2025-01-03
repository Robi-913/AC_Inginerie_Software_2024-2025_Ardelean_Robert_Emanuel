package view.model.builder;

import view.model.BookDTO;

public class BookDTOBuilder {
    private final BookDTO bookDTO;

    public BookDTOBuilder() {
        bookDTO = new BookDTO();
    }

    public BookDTOBuilder setAuthor(String author) {
        bookDTO.setAuthor(author);
        return this;
    }

    public BookDTOBuilder setTitle(String title) {
        bookDTO.setTitle(title);
        return this;
    }

    public BookDTOBuilder setPrice(String price) {
        bookDTO.setPrice(price);
        return this;
    }

    public BookDTOBuilder setStock(String stock) {
        bookDTO.setStock(stock);
        return this;
    }

    public BookDTOBuilder setPublishedDate(String publishedDate) {
        bookDTO.setPublishedDate(publishedDate);
        return this;
    }

    public BookDTO build() {
        return bookDTO;
    }
}