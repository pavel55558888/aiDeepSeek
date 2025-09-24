package org.example.aideepseek.database.service.ucassa.impl;

import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.repo.ucassa.ConfigUCassa;
import org.example.aideepseek.database.service.ucassa.DeleteConfigAllUCassa;
import org.example.aideepseek.database.service.ucassa.GetConfigUCassa;
import org.example.aideepseek.database.service.ucassa.SetConfigUCassa;
import org.example.aideepseek.database.service.ucassa.UpdateConfigUCassa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigUCassaServiceImpl implements GetConfigUCassa, UpdateConfigUCassa, SetConfigUCassa, DeleteConfigAllUCassa {

    @Autowired
    private ConfigUCassa configUCassa;

    @Override
    public List<ConfigUCassaModel> getConfig() {
        return configUCassa.getConfig();
    }

    @Override
    public void updateConfig(ConfigUCassaModel model) {
        configUCassa.updateConfig(model);
    }

    @Override
    public void setConfig(ConfigUCassaModel model) {
        configUCassa.setConfig(model);
    }

    @Override
    public void deleteConfigAll() {
        configUCassa.deleteConfigAll();
    }
}
