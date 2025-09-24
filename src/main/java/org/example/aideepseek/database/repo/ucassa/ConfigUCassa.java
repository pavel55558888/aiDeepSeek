package org.example.aideepseek.database.repo.ucassa;

import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConfigUCassa {
    @Transactional
    public List<ConfigUCassaModel> getConfig();
    @Transactional
    public void updateConfig(ConfigUCassaModel model);
    @Transactional
    public void setConfig(ConfigUCassaModel model);
    @Transactional
    public void deleteConfigAll();
}
