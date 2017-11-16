package ki.oprysko.service;

import ki.oprysko.domain.Country;
import ki.oprysko.domain.Contract;
import ki.oprysko.domain.User;
import ki.oprysko.repository.CountryRepository;
import ki.oprysko.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractServiceTest {

    @Autowired
    private CountryRepository countries;

    @Autowired
    private UserRepository users;

    @Autowired
    private ContractService service;

    @Test
    public void whenApplyLoadThenSaveInDb() {
        User user = this.users.save(new User("Svyatoslav", "Oprysko"));
        Country country = this.countries.save(new Country("Ukraine"));
        Contract contract = this.service.apply(new Contract("",  country, user));
        List<Contract> result = this.service.getAll();
        assertTrue(result.contains(contract));
    }

    @Test
    public void whenFindByPersonThenReturnListOnlyForRerson() {
        User user = this.users.save(new User("Svyatoslav", "Oprysko"));
        Country country = this.countries.save(new Country("Ukraine"));
        Contract contract = this.service.apply(new Contract("", country, user));
        List<Contract> result = this.service.getByUser(user.getId());
        assertThat(result.iterator().next(), is(contract));
    }

}