package ki.oprysko.service;

import ki.oprysko.domain.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User findById(int id);

    List<User> getAllUsers();
}
