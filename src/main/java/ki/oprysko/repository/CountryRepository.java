package ki.oprysko.repository;

import org.springframework.data.repository.CrudRepository;
import ki.oprysko.domain.Country;


public interface CountryRepository extends CrudRepository<Country, Integer> {
}
