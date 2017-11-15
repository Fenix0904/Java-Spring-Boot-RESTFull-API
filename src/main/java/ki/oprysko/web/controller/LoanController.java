package ki.oprysko.web.controller;
import ki.oprysko.domain.Contract;
import ki.oprysko.web.forms.Error;
import ki.oprysko.web.forms.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ki.oprysko.service.BlackListService;
import ki.oprysko.service.LoanService;
import ki.oprysko.web.forms.Result;

import java.util.List;

@RestController
public class LoanController {

    private final LoanService loans;

    private final BlackListService blacklists;

    @Autowired
    public LoanController(final LoanService loans, final BlackListService blacklists) {
        this.loans = loans;
        this.blacklists = blacklists;
    }

    @PostMapping("/apply")
    public Result apply(@RequestBody Contract contract) {
        final Result result;
        if (!this.blacklists.isBlackListPerson(contract.getUser().getId())) {
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

    @GetMapping("/{personId}")
    public List<Contract> findByPersonId(@PathVariable int personId) {
        return this.loans.getByUser(personId);
    }
}
