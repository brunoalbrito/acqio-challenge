package br.com.acqio.challenge.service;

import br.com.acqio.challenge.dto.TransactionDto;
import br.com.acqio.challenge.model.Transaction;
import br.com.acqio.challenge.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public Collection<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction create(@NotNull TransactionDto dto) {
        return save(dto.convertToEntity());
    }

    public Optional<Transaction> update(@NotNull TransactionDto dto,@NotNull Long id) {
        return findById(id).map(transaction -> {
                    transaction.updateInfos(dto);
                    return save(transaction);
                });
    }

    private Transaction save(@NotNull Transaction transaction) {
        return repository.save(transaction);
    }

    private Optional<Transaction> findById(@NotNull Long id) {
        return repository.findById(id);
    }
}
