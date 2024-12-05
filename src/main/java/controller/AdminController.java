package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.UserMapper;
import model.User;
import model.validator.Notification;
import service.order.OrderService;
import service.user.AuthenticationService;
import view.AdminView;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;

import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final OrderService orderService;

    public AdminController(AdminView adminView, AuthenticationService authenticationService, OrderService orderService) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.orderService = orderService;

        this.adminView.addAddButtonListener(new AddUserButtonListener());
        this.adminView.addGenerateReportButtonListener(new GenerateReportButtonListener());

    }

    private class AddUserButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            String role = adminView.getSelectedRole();

            if (username.isEmpty() || password.isEmpty() || role == null) {
                adminView.addDisplayAlertMessage(
                        "Add User Error",
                        "Missing Fields",
                        "Username, password, and role must not be empty."
                );
                return;
            }

            Notification<Boolean> addUserNotification = authenticationService.addUser(username, password, role);

            if (addUserNotification.hasErrors()) {
                adminView.addDisplayAlertMessage(
                        "Add User Error",
                        "Validation Errors",
                        addUserNotification.getFormattedErrors()
                );
            } else if (addUserNotification.getResult()) {
                adminView.addDisplayAlertMessage(
                        "Success",
                        "User Added",
                        "User successfully added to the system."
                );

                UserDTO userDTO = new UserDTOBuilder()
                        .setId(String.valueOf(authenticationService.getLastUsedId()))
                        .setUsername(username)
                        .setRole(role)
                        .build();

                adminView.addUserToObservableList(userDTO);
            } else {
                adminView.addDisplayAlertMessage(
                        "Add User Error",
                        "Database Error",
                        "Failed to add the user to the database. Please try again."
                );
            }
        }
    }

    private class GenerateReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                orderService.generateReport("2024-01-01", "2024-12-31", "user_report.pdf");
                adminView.addDisplayAlertMessage(
                        "Report Generated",
                        "Success",
                        "The report was successfully generated and saved as 'user_report.pdf'."
                );
            } catch (Exception e) {
                adminView.addDisplayAlertMessage(
                        "Generate Report Error",
                        "Failed",
                        "An error occurred while generating the report. Please try again."
                );
                e.printStackTrace();
            }
        }
    }
}
