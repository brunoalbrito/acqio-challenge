package br.com.acqio.challenge.controller;


import br.com.acqio.challenge.dto.TransactionDto;
import br.com.acqio.challenge.model.Transaction;
import br.com.acqio.challenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping("/all")
    public ResponseEntity findAll() {
        List<TransactionDto> transactions = service.findAll().stream()
                .map(Transaction::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TransactionDto dto) {
        TransactionDto savedTransation = service.create(dto).convertToDto();
        return new ResponseEntity(savedTransation, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody TransactionDto dto) {
        return service.update(dto, dto.getId()).map(transaction -> {
            return ResponseEntity.ok(transaction.convertToDto());
        }).orElse(ResponseEntity.notFound().build());
    }
}
