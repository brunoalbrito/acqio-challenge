package br.com.acqio.challenge.dto;

import br.com.acqio.challenge.enums.CardApplication;
import br.com.acqio.challenge.enums.Status;
import br.com.acqio.challenge.model.Transaction;
import br.com.acqio.challenge.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class TransactionDto {

    private Long id;

    private String date;

    private String time;

    private BigDecimal value;

    private String cardApplication;

    private String status;

    private TransactionDto(Long id, String date, String time, BigDecimal value, String cardApplication, String status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.value = value;
        this.cardApplication = cardApplication;
        this.status = status;
    }

    public static TransactionDto of(Long id, String date, String time, BigDecimal value, String cardApplication, String status) {
        return new TransactionDto(id, date, time, value, cardApplication, status);
    }

    public Transaction convertToEntity() {
        LocalDateTime localDateTime = DateUtil.convertToDateTime(this.date, this.getTime());
        CardApplication cardApplication = CardApplication.valueOf(this.cardApplication);
        Status status = Status.valueOf(this.status);
        return Transaction.of(localDateTime, this.value, cardApplication, status);
    }
}
