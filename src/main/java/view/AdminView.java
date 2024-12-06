package view;

import database.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.model.UserDTO;

import java.util.List;

public class AdminView {

    private TableView<UserDTO> userTableView;
    private final ObservableList<UserDTO> usersObservableList;
    private TextField usernameTextField;
    private PasswordField passwordTextField;
    private ComboBox<String> roleComboBox;
    private Button addButton;
    private Button generateReportButton;

    public AdminView(Stage primaryStage, List<UserDTO> users) {
        primaryStage.setTitle("Admin Panel");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 900, 600);
        primaryStage.setScene(scene);

        usersObservableList = FXCollections.observableArrayList(users);
        initTableView(gridPane);

        initAdminOptions(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        userTableView = new TableView<>();

        userTableView.setPlaceholder(new Label("No users to display"));

        userTableView.setPrefHeight(400);
        userTableView.setPrefWidth(800);

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setPrefWidth(200);
        usernameColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<UserDTO, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.setPrefWidth(200);
        roleColumn.setStyle("-fx-alignment: CENTER;");
        TableColumn<UserDTO, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(200);
        idColumn.setStyle("-fx-alignment: CENTER;");

        userTableView.getColumns().addAll(idColumn, usernameColumn, roleColumn);

        userTableView.setItems(usersObservableList);

        gridPane.add(userTableView, 0, 0, 4, 1);
    }

    private void initAdminOptions(GridPane gridPane) {
        Label usernameLabel = new Label("Username:");
        gridPane.add(usernameLabel, 0, 1);

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 1, 1);

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 2);

        passwordTextField = new PasswordField();
        gridPane.add(passwordTextField, 1, 2);

        Label roleLabel = new Label("Role:");
        gridPane.add(roleLabel, 0, 3);

        roleComboBox = new ComboBox<>(FXCollections.observableArrayList(Constants.Roles.ROLES));
        roleComboBox.setPromptText("Select Role");
        gridPane.add(roleComboBox, 1, 3);

        addButton = new Button("Add User");
        gridPane.add(addButton, 0, 4);

        generateReportButton = new Button("Generate Report");
        gridPane.add(generateReportButton, 1, 4);
    }

    public void addAddButtonListener(EventHandler<ActionEvent> addButtonListener) {
        addButton.setOnAction(addButtonListener);
    }

    public void addGenerateReportButtonListener(EventHandler<ActionEvent> generateReportButtonListener) {
        generateReportButton.setOnAction(generateReportButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public String getSelectedRole() {
        return roleComboBox.getValue();
    }

    public void addUserToObservableList(UserDTO userDTO) {
        this.usersObservableList.add(userDTO);
    }

    public void removeUserFromObservableList(UserDTO userDTO) {
        this.usersObservableList.remove(userDTO);
    }

    public TableView<UserDTO> getUserTableView() {
        return userTableView;
    }
}
