package org.example.aideepseek.database.repo.ucassa.impl;

import jakarta.persistence.EntityManager;
import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.repo.ucassa.ConfigUCassa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConfigUcassaImpl implements ConfigUCassa {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ConfigUCassaModel> getConfig() {
        return entityManager.createQuery("from ConfigUCassaModel", ConfigUCassaModel.class).getResultList();
    }

    @Override
    public void updateConfig(ConfigUCassaModel model) {
        entityManager.merge(model);
    }

    @Override
    public void setConfig(ConfigUCassaModel model) {
        entityManager.persist(model);
    }

    @Override
    public void deleteConfigAll(){
        entityManager.createNativeQuery("TRUNCATE TABLE config_ucassa RESTART IDENTITY", ConfigUCassaModel.class)
                .executeUpdate();
    }
}
