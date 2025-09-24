package org.example.aideepseek.controller;

import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.service.ucassa.GetConfigUCassa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ConfigUcassaController {
    @Autowired
    private GetConfigUCassa getConfigUCassa;
    private Logger log = LoggerFactory.getLogger(ConfigUcassaController.class);

    @GetMapping("/config")
    public ResponseEntity<?> getConfig() {
        List<ConfigUCassaModel> config = getConfigUCassa.getConfig();
        if (config.isEmpty()) {
            log.error("Not return config ucassa, table is empty");
            return ResponseEntity.status(500).body("Ошибка: сервер не подгрузил конфиги для онлайн кассы");
        }else {
            log.debug("Return config ucassa");
            return ResponseEntity.ok(config);
        }
    }
}
