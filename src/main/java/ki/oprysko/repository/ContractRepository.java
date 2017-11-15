package ki.oprysko.repository;

import ki.oprysko.domain.Contract;
import ki.oprysko.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContractRepository extends CrudRepository<Contract, Integer> {
    List<Contract> findByUser(User user);
}
