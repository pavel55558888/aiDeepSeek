package org.example.aideepseek.database.repo.http_manual;

import org.example.aideepseek.database.model.HttpManualModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HttpManual {
    public void setHttpManual(HttpManualModel httpManual);
    public List<HttpManualModel> getHttpManual(String url);
    public void deleteHttpManual(long id);
}
