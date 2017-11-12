package ki.oprysko.service;

import ki.oprysko.domain.Country;
import ki.oprysko.domain.Contract;
import ki.oprysko.domain.Person;
import ki.oprysko.repository.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ki.oprysko.repository.PersonRepository;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractServiceTest {

    @Autowired
    private PersonRepository persons;

    @Autowired
    private CountryRepository countries;

    @Autowired
    private LoanService service;

    @Test
    public void whenApplyLoadThenSaveInDb() {
        Person person = this.persons.save(new Person("Svyatoslav", "Oprysko"));
        Country country = this.countries.save(new Country("Ukraine"));
        Contract contract = this.service.apply(new Contract("",  country, person));
        List<Contract> result = this.service.getAll();
        assertTrue(result.contains(contract));
    }

    @Test
    public void whenFindByPersonThenReturnListOnlyForRerson() {
        Person person = this.persons.save(new Person("Svyatoslav", "Oprysko"));
        Country country = this.countries.save(new Country("Ukraine"));
        Contract contract = this.service.apply(new Contract("", country, person));
        List<Contract> result = this.service.getByPerson(person.getId());
        assertThat(result.iterator().next(), is(contract));
    }

}