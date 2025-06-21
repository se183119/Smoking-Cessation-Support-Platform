package com.swpteam.smokingcessation.feature.service.interfaces.membership;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Transaction;

public interface TransactionService {

    public Transaction createTransaction(Account account, double amount);

    public void makeAsPaid(String transactionId);
}
