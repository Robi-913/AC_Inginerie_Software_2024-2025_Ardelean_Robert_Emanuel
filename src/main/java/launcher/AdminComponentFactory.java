package launcher;

import controller.AdminController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import mapper.UserMapper;
import repository.order.OrderRepository;
import repository.order.OrderRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.order.OrderService;
import service.order.OrderServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.AdminView;
import view.model.BookDTO;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {
    private final AdminView adminView;
    private final AdminController adminController;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final RightsRolesRepository rightsRolesRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private static AdminComponentFactory instance;

    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage stage) {
        if (instance == null){
            instance = new AdminComponentFactory(componentsForTest, stage);
        }
        return instance;
    }

    public AdminComponentFactory(Boolean componentsForTest, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        this.orderRepository = new OrderRepositoryMySQL(connection);
        this.orderService = new OrderServiceImpl(orderRepository);
        List<UserDTO> userDTO = UserMapper.convertUserListToUserDTOList(this.authenticationService.listAll());
        this.adminView = new AdminView(stage,userDTO);
        this.adminController = new AdminController(adminView,authenticationService,orderService);
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

}
