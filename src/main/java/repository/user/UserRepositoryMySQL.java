package repository;

import model.User;

import java.util.List;

public class UserRepositoryMySQL implements UserRepository{
    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }
}
