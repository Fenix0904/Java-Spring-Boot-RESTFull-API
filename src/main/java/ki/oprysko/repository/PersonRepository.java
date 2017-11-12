package ki.oprysko.repository;

import ki.oprysko.domain.Person;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, Integer> {
}
