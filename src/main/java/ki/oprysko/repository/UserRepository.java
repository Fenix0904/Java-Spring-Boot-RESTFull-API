package ki.oprysko.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ki.oprysko.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
