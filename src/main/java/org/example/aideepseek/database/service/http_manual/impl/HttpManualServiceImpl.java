package org.example.aideepseek.database.service.http_manual.impl;

import org.example.aideepseek.database.model.HttpManualModel;
import org.example.aideepseek.database.repo.http_manual.HttpManual;
import org.example.aideepseek.database.service.http_manual.DeleteHttpManual;
import org.example.aideepseek.database.service.http_manual.GetHttpManual;
import org.example.aideepseek.database.service.http_manual.SetHttpManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HttpManualServiceImpl implements GetHttpManual, SetHttpManual, DeleteHttpManual {
    @Autowired
    private HttpManual httpManual;

    @Override
    public void deleteHttpManual(long id) {
        httpManual.deleteHttpManual(id);
    }

    @Override
    public List<HttpManualModel> getHttpManual(String url) {
        return httpManual.getHttpManual(url);
    }

    @Override
    public void setHttpManual(HttpManualModel httpManual) {
        this.httpManual.setHttpManual(httpManual);
    }
}
