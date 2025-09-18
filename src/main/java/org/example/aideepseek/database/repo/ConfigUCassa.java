package org.example.aideepseek.database.repo;

import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ConfigUCassa {
    @Transactional
    public Optional<ConfigUCassaModel> getConfig();
    @Transactional
    public void updateConfig(ConfigUCassaModel model);
    @Transactional
    public void setConfig(ConfigUCassaModel model);
}
