package org.example.aideepseek.database.repo.http_manual.impl;

import jakarta.persistence.EntityManager;
import org.example.aideepseek.database.model.HttpManualModel;
import org.example.aideepseek.database.repo.http_manual.HttpManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HttpManualImpl implements HttpManual {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void setHttpManual(HttpManualModel httpManual) {
        entityManager.persist(httpManual);
    }

    @Override
    public List<HttpManualModel> getHttpManual(String url) {
        return entityManager.createQuery("from HttpManualModel where url = :param1", HttpManualModel.class)
                .setParameter("param1", url)
                .getResultList();
    }

    @Override
    public void deleteHttpManual(long id) {
        entityManager.remove(entityManager.find(HttpManualModel.class, id));
    }
}
