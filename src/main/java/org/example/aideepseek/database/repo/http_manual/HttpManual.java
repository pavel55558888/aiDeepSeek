package org.example.aideepseek.database.repo.http_manual;

import org.example.aideepseek.database.model.HttpManualModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HttpManual {
    @Transactional
    public void setHttpManual(HttpManualModel httpManual);
    @Transactional
    public List<HttpManualModel> getHttpManual(String url);
    @Transactional
    public void deleteHttpManual(long id);
}
