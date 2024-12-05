package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import launcher.LoginComponentFactory;
import model.User;
import model.validator.Notification;
import service.user.AuthenticationService;
import model.session.UserSession;
import view.LoginView;

import java.util.Optional;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("LogIn Successful!");

                User loggedInUser = loginNotification.getResult();
                UserSession.getInstance().setUserId(loggedInUser.getId());
                UserSession.getInstance().setUsername(loggedInUser.getUsername());

                Optional<ButtonType> result = loginView.addDisplayAlertMessage(
                        "Role Selection",
                        "Choose your role",
                        "Do you want to log in as Admin or Employee?",
                        new ButtonType("Admin"),
                        new ButtonType("Employee")
                );

                // Verificăm alegerea utilizatorului
                if (result.isPresent() && result.get().getText().equals("Admin")) {
                    // Creează instanța pentru AdminComponentFactory
                    AdminComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                } else {
                    // Creează instanța pentru EmployeeComponentFactory
                    EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                }
            }
        }
    }


    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }
}