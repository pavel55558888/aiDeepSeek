package org.example.aideepseek.database.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionSubscription {
    @Transactional
    void setTransaction();
}
