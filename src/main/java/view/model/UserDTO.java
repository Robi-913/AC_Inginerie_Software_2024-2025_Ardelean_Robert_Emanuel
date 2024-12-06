package view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDTO {
    private StringProperty id;
    private StringProperty username;
    private StringProperty role;

    public StringProperty idProperty() {
        if (id == null) {
            id = new SimpleStringProperty(this, "id");
        }
        return id;
    }

    public void setId(String id) {
        idProperty().set(id);
    }

    public String getId() {
        return idProperty().get();
    }

    public StringProperty usernameProperty() {
        if (username == null) {
            username = new SimpleStringProperty(this, "username");
        }
        return username;
    }

    public void setUsername(String username) {
        usernameProperty().set(username);
    }

    public String getUsername() {
        return usernameProperty().get();
    }

    public StringProperty roleProperty() {
        if (role == null) {
            role = new SimpleStringProperty(this, "role");
        }
        return role;
    }

    public void setRole(String role) {
        roleProperty().set(role);
    }

    public String getRole() {
        return roleProperty().get();
    }
}
