package org.example.aideepseek.database.service.impl;

import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.repo.ConfigUCassa;
import org.example.aideepseek.database.service.GetConfigUCassa;
import org.example.aideepseek.database.service.SetConfigUCassa;
import org.example.aideepseek.database.service.UpdateConfigUCassa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigUCassaServiceImpl implements GetConfigUCassa, UpdateConfigUCassa, SetConfigUCassa {
    @Autowired
    private ConfigUCassa configUCassa;

    @Override
    public Optional<ConfigUCassaModel> getConfig() {
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
}
