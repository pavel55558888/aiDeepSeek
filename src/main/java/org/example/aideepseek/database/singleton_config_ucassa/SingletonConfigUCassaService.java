package org.example.aideepseek.database.singleton_config_ucassa;


import jakarta.annotation.PostConstruct;
import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.service.GetConfigUCassa;
import org.example.aideepseek.database.service.SetConfigUCassa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SingletonConfigUCassaService {
    @Value("${ucassa.shop.id}")
    private long shopId;
    @Value("${ucassa.key}")
    private String key;
    @Value("${ucassa.price}")
    private double price;
    @Value("${ucassa.description}")
    private String description;
    @Autowired
    private GetConfigUCassa getConfigUCassa;
    @Autowired
    private SetConfigUCassa setConfigUCassa;
    private Logger log = LoggerFactory.getLogger(SingletonConfigUCassaService.class);

    @PostConstruct
    public void init() {
        Optional<ConfigUCassaModel> configOpt = getConfigUCassa.getConfig();

        if (configOpt.isEmpty()) {
            ConfigUCassaModel newConfig = new ConfigUCassaModel(shopId, key, description, price);

            setConfigUCassa.setConfig(newConfig);

            log.info("Set config ucassa from application.properties");
        } else {
            log.info("Config ucassa not empty");
        }
    }

}
