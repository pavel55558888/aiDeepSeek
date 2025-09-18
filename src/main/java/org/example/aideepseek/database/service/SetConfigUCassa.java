package org.example.aideepseek.database.service;

import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.springframework.stereotype.Service;

@Service
public interface SetConfigUCassa {
    public void setConfig(ConfigUCassaModel model);
}
