package ki.oprysko.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ki.oprysko.domain.BlackList;
import ki.oprysko.domain.Person;
import ki.oprysko.repository.BlackListRepository;
import ki.oprysko.repository.PersonRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlackListServiceTest {

    @Autowired
    private PersonRepository persons;

    @Autowired
    private BlackListRepository blacklists;

    @Autowired
    private BlackListService service;

    @Test
    public void whenPersonInBlackListThenReturnTrue() {
        Person person = this.persons.save(new Person("Svyatoslav", "Oprysko"));
        this.blacklists.save(new BlackList(person));
        boolean result = this.service.isBlackListPerson(person.getId());
        assertTrue(result);
    }

    @Test
    public void whenBlackListEmptyThenAnyPersonNotIn() {
        Person person = this.persons.save(new Person("Svyatoslav", "Oprysko"));
        boolean result = this.service.isBlackListPerson(person.getId());
        assertFalse(result);
    }
}