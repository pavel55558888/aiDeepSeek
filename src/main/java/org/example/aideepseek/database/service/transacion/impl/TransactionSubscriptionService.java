package org.example.aideepseek.database.service.transacion.impl;

import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.repo.transaction.TransactionSubscription;
import org.example.aideepseek.database.service.transacion.GetTransactionByEmail;
import org.example.aideepseek.database.service.transacion.SetTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionSubscriptionService implements SetTransaction, GetTransactionByEmail {
    @Autowired
    TransactionSubscription transactionSubscription;
    @Override
    public void setTransaction(TransactionSubscriptionModel transaction) {
        transactionSubscription.setTransaction(transaction);
    }

    @Override
    public List<TransactionSubscriptionModel> getTransaction(String email) {
        return transactionSubscription.getTransaction(email);
    }
}
