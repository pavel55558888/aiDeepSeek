package org.example.aideepseek.database.subscription;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.aideepseek.database.model.SubscriptionModel;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.database.model.enums.Status;
import org.example.aideepseek.database.repo.subscription.impl.SubscriptionImpl;
import org.example.aideepseek.security.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SubscriptionImpl subscription;

    @Test
    public void getSubscriptionByEmail_ReturnsSubscription_WhenEmailExists() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        SubscriptionModel expectedSubscription = new SubscriptionModel();
        expectedSubscription.setUser(user);

        @SuppressWarnings("unchecked")
        TypedQuery<SubscriptionModel> mockQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(
                "SELECT s FROM SubscriptionModel s JOIN s.user u WHERE u.email = :email",
                SubscriptionModel.class))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("email", email)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedSubscription);

        // When
        SubscriptionModel result = subscription.getSubscriptionByEmail(email);

        // Then
        assertThat(result).isEqualTo(expectedSubscription);
        verify(entityManager).createQuery(
                "SELECT s FROM SubscriptionModel s JOIN s.user u WHERE u.email = :email",
                SubscriptionModel.class);
        verify(mockQuery).setParameter("email", email);
        verify(mockQuery).getSingleResult();
    }

    @Test
    public void setSubscription_PersistsSubscription() {
        // Given
        SubscriptionModel subscriptionModel = new SubscriptionModel();

        // When
        subscription.setSubscription(subscriptionModel);

        // Then
        verify(entityManager).persist(subscriptionModel);
    }

    @Test
    public void updateSubscription_MergesSubscription() {
        // Given
        SubscriptionModel subscriptionModel = new SubscriptionModel();

        // When
        subscription.updateSubscription(subscriptionModel);

        // Then
        verify(entityManager).merge(subscriptionModel);
    }

    @Test
    public void purchaseOfSubscription_WhenSubscriptionTrue_UpdatesToActive() {
        // Given
        String email = "buyer@example.com";
        User user = new User();
        user.setEmail(email);

        TransactionSubscriptionModel transaction = new TransactionSubscriptionModel();
        transaction.setUser(user);

        SubscriptionModel existingSubscription = new SubscriptionModel();
        existingSubscription.setUser(user);
        existingSubscription.setStatus(Status.INACTIVE);
        existingSubscription.setFreeAttempt(2);

        @SuppressWarnings("unchecked")
        TypedQuery<SubscriptionModel> mockQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(
                "SELECT s FROM SubscriptionModel s JOIN s.user u WHERE u.email = :email",
                SubscriptionModel.class))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("email", email)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(existingSubscription);

        // When
        subscription.purchaseOfSubscription(transaction, true, 0);

        // Then
        verify(entityManager).persist(transaction);

        assertThat(existingSubscription.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(existingSubscription.getTimestamp()).isNotNull();
        assertThat(existingSubscription.getFreeAttempt()).isEqualTo(2); // не изменилось

        verify(entityManager).merge(existingSubscription);
    }

    @Test
    public void purchaseOfSubscription_WhenSubscriptionFalse_IncrementsFreeAttempt() {
        // Given
        String email = "buyer@example.com";
        User user = new User();
        user.setEmail(email);

        TransactionSubscriptionModel transaction = new TransactionSubscriptionModel();
        transaction.setUser(user);

        SubscriptionModel existingSubscription = new SubscriptionModel();
        existingSubscription.setUser(user);
        existingSubscription.setFreeAttempt(3); // было 3

        @SuppressWarnings("unchecked")
        TypedQuery<SubscriptionModel> mockQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(
                "SELECT s FROM SubscriptionModel s JOIN s.user u WHERE u.email = :email",
                SubscriptionModel.class))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("email", email)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(existingSubscription);

        int attempt = 2;

        // When
        subscription.purchaseOfSubscription(transaction, false, attempt);

        // Then
        verify(entityManager).persist(transaction);
        assertThat(existingSubscription.getFreeAttempt()).isEqualTo(3 + attempt); // 5
        assertThat(existingSubscription.getStatus()).isNotEqualTo(Status.ACTIVE);
        verify(entityManager).merge(existingSubscription);
    }
}