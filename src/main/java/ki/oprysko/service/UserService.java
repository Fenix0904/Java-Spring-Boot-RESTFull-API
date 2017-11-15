package ki.oprysko.service;

import ki.oprysko.domain.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    List<User> getAllUsers();
}
