package org.example.aideepseek.database.transaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.repo.transaction.impl.TransactionSubscriptionImpl;
import org.example.aideepseek.security.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionSubscriptionTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TransactionSubscriptionImpl transactionSubscription;

    @Test
    public void setTransaction_PersistsTransaction() {
        // Given
        TransactionSubscriptionModel transaction = new TransactionSubscriptionModel();

        // When
        transactionSubscription.setTransaction(transaction);

        // Then
        verify(entityManager).persist(transaction);
    }

    @Test
    public void getTransaction_ReturnsListOfTransactions_ByEmail() {
        // Given
        String email = "user@example.com";

        User user = new User();
        user.setEmail(email);

        TransactionSubscriptionModel tx1 = new TransactionSubscriptionModel();
        tx1.setUser(user);

        TransactionSubscriptionModel tx2 = new TransactionSubscriptionModel();
        tx2.setUser(user);

        List<TransactionSubscriptionModel> expectedTransactions = List.of(tx1, tx2);

        @SuppressWarnings("unchecked")
        TypedQuery<TransactionSubscriptionModel> mockQuery = mock(TypedQuery.class);

        when(entityManager.createQuery(
                "SELECT t FROM TransactionSubscriptionModel t JOIN t.user u WHERE u.email = :email",
                TransactionSubscriptionModel.class))
                .thenReturn(mockQuery);

        when(mockQuery.setParameter("email", email)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedTransactions);

        // When
        List<TransactionSubscriptionModel> result = transactionSubscription.getTransaction(email);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(tx1, tx2);

        verify(entityManager).createQuery(
                "SELECT t FROM TransactionSubscriptionModel t JOIN t.user u WHERE u.email = :email",
                TransactionSubscriptionModel.class);
        verify(mockQuery).setParameter("email", email);
        verify(mockQuery).getResultList();
    }
}