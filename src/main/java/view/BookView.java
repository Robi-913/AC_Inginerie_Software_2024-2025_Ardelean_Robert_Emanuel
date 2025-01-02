package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import view.model.BookDTO;

import java.util.List;

public class BookView {
    private TableView<BookDTO> bookTableView;
    private final ObservableList<BookDTO> booksObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField priceTextField;
    private TextField stockTextField;
    private TextField sellStockTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Label priceLabel;
    private Label stockLabel;
    private Label sellStockLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button sellerButton;

    public BookView(Stage primaryStage, List<BookDTO> books) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        Scene scene = new Scene(gridPane, 1100, 600);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initializeGridPage(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<>();

        bookTableView.setPlaceholder(new Label("No books to display"));

        bookTableView.setPrefHeight(400);
        bookTableView.setPrefWidth(1000);

        TableColumn<BookDTO, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);
        titleColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<BookDTO, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setPrefWidth(200);
        authorColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<BookDTO, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(200);
        priceColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<BookDTO, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setPrefWidth(200);
        stockColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<BookDTO, String> publishedDateColumn = new TableColumn<>("PublishedDate");
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        publishedDateColumn.setPrefWidth(200);
        publishedDateColumn.setStyle("-fx-alignment: CENTER;");

        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, stockColumn, publishedDateColumn);

        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView, 0, 0, 6, 1);
    }

    private void initSaveOptions(GridPane gridPane) {
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 1, 2);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 2, 2);

        stockLabel = new Label("Stock");
        gridPane.add(stockLabel, 3, 2);

        stockTextField = new TextField();
        gridPane.add(stockTextField, 4, 2);

        sellStockLabel = new Label("Sell Stock");
        gridPane.add(sellStockLabel, 1, 4);

        sellStockTextField = new TextField();
        gridPane.add(sellStockTextField, 2, 4);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 1, 3);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 2, 3);

        sellerButton = new Button("Sell Book");
        gridPane.add(sellerButton, 1, 5);


    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener) {
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSellerButtonListener(EventHandler<ActionEvent> sellerButtonListener) {
        sellerButton.setOnAction(sellerButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getAuthor() {
        return authorTextField.getText();
    }

    public String getStock() {
        return stockTextField.getText();
    }

    public String getPrice() {
        return priceTextField.getText();
    }

    public String getSellStock() {
        return sellStockTextField.getText();
    }

    public void addBookToObservableList(BookDTO bookDTO) {
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookFromObservableList(BookDTO bookDTO) {
        this.booksObservableList.remove(bookDTO);
    }

    public void updateBookInObservableList(BookDTO bookDTO) {
        int index = booksObservableList.indexOf(bookDTO);
        if (index >= 0) {
            booksObservableList.set(index, bookDTO);
        }
    }

    public TableView<BookDTO> getBookTableView() {
        return bookTableView;
    }
}
