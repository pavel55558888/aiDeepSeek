package org.example.aideepseek.database.service.ucassa;

import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GetConfigUCassa {
    public List<ConfigUCassaModel> getConfig();
}
