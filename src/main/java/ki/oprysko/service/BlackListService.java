package ki.oprysko.service;


import ki.oprysko.domain.User;

public interface BlackListService {
    boolean isBlackListPerson(int personId);
    void addUser(User user);
}
