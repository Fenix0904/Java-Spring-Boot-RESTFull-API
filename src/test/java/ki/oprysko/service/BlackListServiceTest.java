package ki.oprysko.service;

import ki.oprysko.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ki.oprysko.domain.BlackList;
import ki.oprysko.domain.User;
import ki.oprysko.repository.BlackListRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlackListServiceTest {
    @Autowired
    private UserRepository users;

    @Autowired
    private BlackListRepository blacklists;

    @Autowired
    private BlackListService service;

    @Test
    public void whenPersonInBlackListThenReturnTrue() {
        User user = this.users.save(new User("Svyatoslav", "Oprysko"));
        this.blacklists.save(new BlackList(user));
        boolean result = this.service.isBlackListPerson(user.getId());
        assertTrue(result);
    }

    @Test
    public void whenBlackListEmptyThenAnyPersonNotIn() {
        User user = this.users.save(new User("Svyatoslav", "Oprysko"));
        boolean result = this.service.isBlackListPerson(user.getId());
        assertFalse(result);
    }
}