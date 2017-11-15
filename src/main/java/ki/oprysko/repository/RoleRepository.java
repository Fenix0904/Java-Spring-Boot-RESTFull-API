package ki.oprysko.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ki.oprysko.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
