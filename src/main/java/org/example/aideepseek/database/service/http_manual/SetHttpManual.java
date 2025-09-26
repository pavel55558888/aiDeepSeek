package org.example.aideepseek.database.service.http_manual;

import org.example.aideepseek.database.model.HttpManualModel;
import org.springframework.stereotype.Service;

@Service
public interface SetHttpManual {
    public void setHttpManual(HttpManualModel httpManual);
}
