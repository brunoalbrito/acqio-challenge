package br.com.acqio.challenge.model;


import br.com.acqio.challenge.dto.TransactionDto;
import br.com.acqio.challenge.enums.CardApplication;
import br.com.acqio.challenge.enums.Status;
import br.com.acqio.challenge.util.DateUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @EqualsAndHashCode.Include
    private LocalDateTime dateTime;

    @EqualsAndHashCode.Include
    private BigDecimal value;

    private CardApplication cardApplication;

    private Status status;

    protected Transaction() {
    }

    private Transaction(LocalDateTime dateTime, BigDecimal value, CardApplication cardApplication, Status status) {
        this.dateTime = dateTime;
        this.value = value;
        this.cardApplication = cardApplication;
        this.status = status;
    }

    public static Transaction of(LocalDateTime dateTime, BigDecimal value, CardApplication cardApplication, Status status) {
        return new Transaction(dateTime, value, cardApplication, status);
    }

    public TransactionDto convertToDto() {
        String date = DateUtil.getDateFromLocalDateTime(this.dateTime);
        String time = DateUtil.getTimeFromLocalDateTime(this.dateTime);
        return TransactionDto.of(this.id, date, time, this.value, this.cardApplication.name(), this.status.name());
    }

    public void updateInfos(TransactionDto dto) {
        this.dateTime = DateUtil.convertToDateTime(dto.getDate(), dto.getTime());
        this.value = dto.getValue();
        this.cardApplication = CardApplication.valueOf(dto.getCardApplication());
        this.status = Status.valueOf(dto.getStatus());
    }
}
