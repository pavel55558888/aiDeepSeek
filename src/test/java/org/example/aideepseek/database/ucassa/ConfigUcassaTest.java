package org.example.aideepseek.database.ucassa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.repo.ucassa.impl.ConfigUcassaImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConfigUcassaTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ConfigUcassaImpl configUcassa;

    @Test
    public void getConfig_ReturnsAllConfigs() {
        // Given
        ConfigUCassaModel config1 = new ConfigUCassaModel();
        ConfigUCassaModel config2 = new ConfigUCassaModel();
        List<ConfigUCassaModel> expected = List.of(config1, config2);

        @SuppressWarnings("unchecked")
        TypedQuery<ConfigUCassaModel> mockQuery = mock(TypedQuery.class);
        when(entityManager.createQuery("from ConfigUCassaModel", ConfigUCassaModel.class))
                .thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expected);

        // When
        List<ConfigUCassaModel> result = configUcassa.getConfig();

        // Then
        assertThat(result).hasSize(2).containsExactly(config1, config2);
        verify(entityManager).createQuery("from ConfigUCassaModel", ConfigUCassaModel.class);
        verify(mockQuery).getResultList();
    }

    @Test
    public void setConfig_PersistsNewConfig() {
        // Given
        ConfigUCassaModel config = new ConfigUCassaModel();

        // When
        configUcassa.setConfig(config);

        // Then
        verify(entityManager).persist(config);
    }

    @Test
    public void updateConfig_MergesExistingConfig() {
        // Given
        ConfigUCassaModel config = new ConfigUCassaModel();

        // When
        configUcassa.updateConfig(config);

        // Then
        verify(entityManager).merge(config);
    }

    @Test
    public void deleteConfigAll_TruncatesTable() {
        // Given
        @SuppressWarnings("unchecked")
        Query mockNativeQuery = mock(Query.class);
        when(entityManager.createNativeQuery(
                "TRUNCATE TABLE config_ucassa RESTART IDENTITY",
                ConfigUCassaModel.class))
                .thenReturn(mockNativeQuery);
        when(mockNativeQuery.executeUpdate()).thenReturn(0);

        // When
        configUcassa.deleteConfigAll();

        // Then
        verify(entityManager).createNativeQuery(
                "TRUNCATE TABLE config_ucassa RESTART IDENTITY",
                ConfigUCassaModel.class);
        verify(mockNativeQuery).executeUpdate();
    }
}