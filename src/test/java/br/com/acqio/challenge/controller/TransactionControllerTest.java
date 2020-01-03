package br.com.acqio.challenge.controller;

import br.com.acqio.challenge.dto.TransactionDto;
import br.com.acqio.challenge.enums.CardApplication;
import br.com.acqio.challenge.enums.Status;
import br.com.acqio.challenge.model.Transaction;
import br.com.acqio.challenge.service.TransactionService;
import br.com.acqio.challenge.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        this.transaction = Transaction.of(DateUtil.convertToDateTime("17/01/2019", "13:52:33"), BigDecimal.TEN, CardApplication.CREDITO, Status.SUCCESS);
    }

    @Test
    public void deveEntenderBuscaTransactionsRetornandoUmItem() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(this.transaction));

        mockMvc.perform(get("/transaction/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].date", is("17/01/2019")))
                .andExpect(jsonPath("$[0].time", is("13:52:33")))
                .andExpect(jsonPath("$[0].value", is(10)))
                .andExpect(jsonPath("$[0].cardApplication", is(CardApplication.CREDITO.name())))
                .andExpect(jsonPath("$[0].status", is(Status.SUCCESS.name())))
                .andReturn().getResponse();

        verify(service, atLeastOnce()).findAll();
    }

    @Test
    public void deveEntenderCriarRetornandoCreated() throws Exception {
        when(service.create(any(TransactionDto.class))).thenReturn(transaction);

        mockMvc.perform(post("/transaction")
                .content(objectMapper.writeValueAsString(transaction.convertToDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse();

        verify(service, atLeastOnce()).create(any(TransactionDto.class));
    }

    @Test
    public void deveEntenderUpdateRetornandoSucess() throws Exception {
        TransactionDto dto = transaction.convertToDto();
        dto.setId(1L);

        when(service.update(any(TransactionDto.class), anyLong())).thenReturn(Optional.of(transaction));

        mockMvc.perform(put("/transaction")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        verify(service, atLeastOnce()).update(any(TransactionDto.class), anyLong());
    }
}
