package repository.security;

import model.Right;
import model.Role;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RightsRolesRepositoryMySQL implements RightsRolesRepository {

    private final Connection connection;

    public RightsRolesRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRole(String role) {
        String sql = "INSERT IGNORE INTO ROLE (id, role) VALUES (null, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding role: " + role);
            e.printStackTrace();
        }
    }

    @Override
    public void addRight(String right) {
        String sql = "INSERT IGNORE INTO `RIGHT` (id, `right`) VALUES (null, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, right);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding right: " + right);
            e.printStackTrace();
        }
    }

    @Override
    public Role findRoleByTitle(String role) {
        String sql = "SELECT * FROM ROLE WHERE role = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String roleName = resultSet.getString("role");
                    return new Role(id, roleName, null);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding role by title: " + role);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Role findRoleById(Long roleId) {
        String sql = "SELECT * FROM ROLE WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleName = resultSet.getString("role");
                    return new Role(roleId, roleName, null);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding role by id: " + roleId);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Right findRightByTitle(String right) {
        String sql = "SELECT * FROM `RIGHT` WHERE `right` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, right);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String rightName = resultSet.getString("right");
                    return new Right(id, rightName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding right by title: " + right);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addRolesToUser(User user, List<Role> roles) {
        String sql = "INSERT INTO USER_ROLE (id, user_id, role_id) VALUES (null, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Role role : roles) {
                statement.setLong(1, user.getId());
                statement.setLong(2, role.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error adding roles to user: " + user.getUsername());
            e.printStackTrace();
        }
    }

    @Override
    public List<Role> findRolesForUser(Long userId) {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT role_id FROM USER_ROLE WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long roleId = resultSet.getLong("role_id");
                    Role role = findRoleById(roleId);
                    if (role != null) {
                        roles.add(role);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding roles for user with ID: " + userId);
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public void addRoleRight(Long roleId, Long rightId) {
        String sql = "INSERT IGNORE INTO ROLE_RIGHT (id, role_id, right_id) VALUES (null, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, roleId);
            statement.setLong(2, rightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding role-right mapping for roleId: " + roleId + ", rightId: " + rightId);
            e.printStackTrace();
        }
    }
}
