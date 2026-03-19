package com.corebank.corebank.controller;

import com.corebank.corebank.service.BankStatementService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TransactionStatementController {

    private final BankStatementService service;

    public TransactionStatementController(BankStatementService service) {
        this.service = service;
    }

    @GetMapping("/accounts/{id}/statement")
    public ResponseEntity<byte[]> downloadStatement(@PathVariable Long id) {

        byte[] pdf = service.generateStatement(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=statement-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
