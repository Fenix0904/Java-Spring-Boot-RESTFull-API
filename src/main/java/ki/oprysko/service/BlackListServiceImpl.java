package ki.oprysko.service;

import ki.oprysko.repository.BlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ki.oprysko.domain.User;

@Service
public class BlackListServiceImpl implements BlackListService {

    private final BlackListRepository repository;

    @Autowired
    public BlackListServiceImpl(final BlackListRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isBlackListPerson(int personId) {
        return this.repository.findByUser(new User(personId)) != null;
    }
}
