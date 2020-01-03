package br.com.acqio.challenge.service;


import br.com.acqio.challenge.dto.TransactionDto;
import br.com.acqio.challenge.enums.CardApplication;
import br.com.acqio.challenge.enums.Status;
import br.com.acqio.challenge.model.Transaction;
import br.com.acqio.challenge.repository.TransactionRepository;
import br.com.acqio.challenge.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        this.transaction = Transaction.of(DateUtil.convertToDateTime("17/01/2019", "13:52:33"), BigDecimal.TEN, CardApplication.CREDITO, Status.SUCCESS);
    }

    @Test
    public void deveEntenderCriaTransacaoComSucesso() {
        when(repository.save(any(Transaction.class))).thenReturn(this.transaction);

        Transaction obtido = service.create(this.transaction.convertToDto());

        assertEquals(obtido.getValue(), BigDecimal.TEN);
        assertEquals(obtido.getCardApplication(), CardApplication.CREDITO);
        assertEquals(obtido.getStatus(), Status.SUCCESS);
        assertEquals(obtido.getDateTime(), DateUtil.convertToDateTime("17/01/2019", "13:52:33"));
    }

    @Test
    public void deveEntenderBuscaPorTodasAsTransacoes() {
        when(repository.findAll()).thenReturn(Arrays.asList(this.transaction));

        Collection<Transaction> obtido = service.findAll();

        assertEquals(1, obtido.size());
    }

    @Test
    public void deveEntenderAtualizacaoDeTransacao() {
        TransactionDto transactionInfoAtualizadas = criaTransacaoParaAtualizacao();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(transaction));
        when(repository.save(any(Transaction.class))).thenReturn(transactionInfoAtualizadas.convertToEntity());


        Optional obtido = service.update(transactionInfoAtualizadas, 1L);

        assertTrue(obtido.isPresent());
    }

    private TransactionDto criaTransacaoParaAtualizacao() {
        TransactionDto transactionDto = transaction.convertToDto();
        transactionDto.setValue(new BigDecimal(250.55));
        transactionDto.setStatus(CardApplication.DEBITO.name());
        transactionDto.setStatus(Status.CANCELED.name());

        return transactionDto;
    }
}
