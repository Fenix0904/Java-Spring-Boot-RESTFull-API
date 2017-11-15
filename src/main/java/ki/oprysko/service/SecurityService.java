package ki.oprysko.service;


public interface SecurityService {
    String findLoggedInUser();

    void autoLogin(String username, String password);
}
