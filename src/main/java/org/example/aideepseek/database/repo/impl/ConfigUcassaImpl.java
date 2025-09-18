package org.example.aideepseek.database.repo.impl;

import jakarta.persistence.EntityManager;
import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.repo.ConfigUCassa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConfigUcassaImpl implements ConfigUCassa {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<ConfigUCassaModel> getConfig() {
        return Optional.ofNullable(entityManager.find(ConfigUCassaModel.class, 1L));
    }

    @Override
    public void updateConfig(ConfigUCassaModel model) {
        entityManager.merge(model);
    }

    @Override
    public void setConfig(ConfigUCassaModel model) {
        entityManager.persist(model);
    }
}
