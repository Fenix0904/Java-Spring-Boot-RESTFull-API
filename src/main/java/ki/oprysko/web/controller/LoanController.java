package ki.oprysko.web.controller;
import ki.oprysko.domain.Contract;
import ki.oprysko.domain.User;
import ki.oprysko.service.UserService;
import ki.oprysko.web.forms.Error;
import ki.oprysko.web.forms.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ki.oprysko.service.BlackListService;
import ki.oprysko.service.LoanService;
import ki.oprysko.web.forms.Result;

import java.util.List;

@RestController
public class LoanController {

    private final LoanService loans;
    private final UserService userService;
    private final BlackListService blacklists;

    @Autowired
    public LoanController(final LoanService loans, final BlackListService blacklists, UserService userService) {
        this.loans = loans;
        this.blacklists = blacklists;
        this.userService = userService;
    }

    @PostMapping("/apply")
    public Result apply(@RequestBody Contract contract) {
        final Result result;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        contract.setUser(user);
        if (!this.blacklists.isBlackListPerson(user.getId())) {
            result = new Success<>(
                    this.loans.apply(contract)
            );
        } else {
            result = new Error(String.format("User %s in blacklist", contract.getUser().getId()));
        }
        return result;
    }

    @GetMapping("/get-all-contracts")
    public List<Contract> getAll() {
        return this.loans.getAll();
    }

    @GetMapping("/{userId}")
    public List<Contract> findByPersonId(@PathVariable int userId) {
        return this.loans.getByUser(userId);
    }
}
