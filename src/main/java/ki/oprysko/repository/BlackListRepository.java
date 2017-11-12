package ki.oprysko.repository;

import ki.oprysko.domain.BlackList;
import org.springframework.data.repository.CrudRepository;
import ki.oprysko.domain.Person;

public interface BlackListRepository extends CrudRepository<BlackList, Integer> {

    BlackList findByPerson(Person person);
}
