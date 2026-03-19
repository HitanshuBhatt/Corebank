package com.corebank.corebank.service;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.repository.TransactionRepository;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BankStatementService {

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    public BankStatementService(AccountRepository accountRepo,
                                TransactionRepository transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    public byte[] generateStatement(Long accountId) throws DocumentException {

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Transaction> transactions = transactionRepo.findByAccountId(accountId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document, out);
        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        document.add(new Paragraph("Bank Statement", titleFont));
        document.add(new Paragraph("\n"));

        // Account Info
        document.add(new Paragraph("Account Number: " + account.getAccountNumber()));
        document.add(new Paragraph("Account Type   : " + account.getType()));
        document.add(new Paragraph("Account Name   : " +
                (account.getAccountName() != null ? account.getAccountName() : "-")));
        document.add(new Paragraph("\n\n"));

        // Table setup
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.addCell(new PdfPCell(new Paragraph("Date")));
        table.addCell(new PdfPCell(new Paragraph("Type")));
        table.addCell(new PdfPCell(new Paragraph("Amount")));
        table.addCell(new PdfPCell(new Paragraph("Description")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Add transactions
        for (Transaction t : transactions) {
            table.addCell(t.getTimestamp().format(formatter));
            table.addCell(t.getType().toString());
            table.addCell(String.valueOf(t.getAmount()));
            table.addCell(t.getDescription() != null ? t.getDescription() : "-");
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}
