package ki.oprysko.service;

import com.google.common.collect.Lists;
import ki.oprysko.domain.Contract;
import ki.oprysko.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ki.oprysko.repository.ContractRepository;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository repository;

    @Autowired
    public ContractServiceImpl(final ContractRepository repository) {
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
    public List<Contract> getByUser(int personId) {
        return this.repository.findByUser(new User(personId));
    }
}
