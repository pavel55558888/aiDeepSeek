package org.example.aideepseek.database.service.transacion;

import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.springframework.stereotype.Service;

@Service
public interface SetTransaction {
    void setTransaction(TransactionSubscriptionModel transaction);
}
