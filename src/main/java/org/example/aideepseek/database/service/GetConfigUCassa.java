package org.example.aideepseek.database.service;

import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface GetConfigUCassa {
    public Optional<ConfigUCassaModel> getConfig();
}
