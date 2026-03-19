package com.corebank.corebank.model.enums;

public enum TransactionType {

    // Money added by user or system
    DEPOSIT,

    // Money withdrawn by user
    WITHDRAW,

    // Money transferred to another account
    TRANSFER,

    // When customer applies for a loan (just application)
    LOAN_APPLIED,

    // When loan is APPROVED and credited to main account
    LOAN_CREDIT,

    // ✅ New: Bill payment made from an account
    BILL_PAYMENT
}
