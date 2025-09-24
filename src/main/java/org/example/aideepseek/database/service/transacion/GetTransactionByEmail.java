package org.example.aideepseek.database.service.transacion;

import org.example.aideepseek.database.model.TransactionSubscriptionModel;

import java.util.List;

public interface GetTransactionByEmail {
    List<TransactionSubscriptionModel> getTransaction(String email);
}
