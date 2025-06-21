package com.swpteam.smokingcessation.feature.service.impl.membership;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.feature.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.entity.Transaction;
import com.swpteam.smokingcessation.feature.repository.TransactionRepository;
import com.swpteam.smokingcessation.domain.enums.TransactionStatus;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.feature.service.interfaces.membership.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    AccountRepository accountRepository;

    @Override
    @Transactional
    public Transaction createTransaction(Account account, double amount) {
        accountRepository.findById(account.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Transaction transaction = Transaction.startTransaction(account);

        transaction.setAmount(amount);

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void makeAsPaid(String transactionId) {
        Transaction transaction = transactionRepository.findByIdAndIsDeletedFalse(transactionId)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        transaction.setStatus(TransactionStatus.COMPLETED);

        transactionRepository.save(transaction);
    }
}
