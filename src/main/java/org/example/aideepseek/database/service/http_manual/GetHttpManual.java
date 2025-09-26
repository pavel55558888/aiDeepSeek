package org.example.aideepseek.database.service.http_manual;

import org.example.aideepseek.database.model.HttpManualModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GetHttpManual {
    public List<HttpManualModel> getHttpManual(String url);
}
