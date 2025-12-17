package org.example.aideepseek.database.http_manual;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.aideepseek.database.model.HttpManualModel;
import org.example.aideepseek.database.repo.http_manual.impl.HttpManualImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HttpManualTest {
    @Mock
    private EntityManager entityManager;
    @InjectMocks
    private HttpManualImpl httpManual;

    @Test
    public void setHttpManual_WhenHttpModel_ReturnNull(){
        //Given
        HttpManualModel httpManualModel =
                new HttpManualModel(
                        "http://localhost:8080/test",
                        new Timestamp(System.currentTimeMillis()),
                        "manual{}"
                );
        //When
        httpManual.setHttpManual(httpManualModel);
        //Then
        verify(entityManager).persist(httpManualModel);
    }

    @Test
    public void getHttpManual_WhenUrl_ReturnHttpManual() {
        //Given
        HttpManualModel model1 =
                new HttpManualModel(
                        "http://localhost:8080/test",
                        new Timestamp(System.currentTimeMillis()),
                        "manual{}"
                );

        List<HttpManualModel> expectedList = List.of(model1, model1);

        @SuppressWarnings("unchecked")
        TypedQuery<HttpManualModel> mockQuery = Mockito.mock(TypedQuery.class);
        //When
        when(entityManager.createQuery(
                "from HttpManualModel where url = :param1",
                HttpManualModel.class))
                .thenReturn(mockQuery);

        when(mockQuery.setParameter("param1", "http://localhost:8080/test")).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(expectedList);

        //Then
        List<HttpManualModel> result = httpManual.getHttpManual("http://localhost:8080/test");

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(model1, model1);

        verify(entityManager).createQuery("from HttpManualModel where url = :param1", HttpManualModel.class);
        verify(mockQuery).setParameter("param1", "http://localhost:8080/test");
        verify(mockQuery).getResultList();
    }

    @Test
    public void deleteHttpManualWhenId_ReturnNull(){
        //Given
        long id = 123L;
        HttpManualModel httpManualModel = new HttpManualModel(
                "http://localhost:8080/test",
                new Timestamp(System.currentTimeMillis()),
                "manual{}"
        );
        httpManualModel.setId(id);
        //When
        when(entityManager.find(HttpManualModel.class, id)).thenReturn(httpManualModel);
        //Then
        httpManual.deleteHttpManual(id);

        verify(entityManager).find(HttpManualModel.class, id);
        verify(entityManager).remove(httpManualModel);
    }

}
