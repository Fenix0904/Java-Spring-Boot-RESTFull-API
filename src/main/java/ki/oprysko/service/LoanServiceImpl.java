package ki.oprysko.service;

import com.google.common.collect.Lists;
import ki.oprysko.domain.Contract;
import ki.oprysko.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ki.oprysko.repository.ContractRepository;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final ContractRepository repository;

    @Autowired
    public LoanServiceImpl(final ContractRepository repository) {
        this.repository = repository;
    }

    @Override
    public Contract apply(final Contract contract) {
        return this.repository.save(contract);
    }

    @Override
    public List<Contract> getAll() {
        return Lists.newArrayList(this.repository.findAll());
    }

    @Override
    public List<Contract> getByPerson(int personId) {
        return this.repository.findByPerson(new Person(personId));
    }
}
