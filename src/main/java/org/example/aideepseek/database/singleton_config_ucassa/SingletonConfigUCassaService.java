package org.example.aideepseek.database.singleton_config_ucassa;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.example.aideepseek.database.service.ucassa.DeleteConfigAllUCassa;
import org.example.aideepseek.database.service.ucassa.GetConfigUCassa;
import org.example.aideepseek.database.service.ucassa.SetConfigUCassa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SingletonConfigUCassaService {
    @Value("${ucassa.shop.id}")
    private long shopId;
    @Value("${ucassa.key}")
    private String key;
    @Value("${ucassa.price}")
    private List<Double> price;
    @Value("${ucassa.description}")
    private List<String> description;
    @Value("${ucassa.type}")
    private List<String> type;
    @Autowired
    private GetConfigUCassa getConfigUCassa;
    @Autowired
    private SetConfigUCassa setConfigUCassa;
    @Autowired
    private DeleteConfigAllUCassa deleteConfigAllUCassa;
    private Logger log = LoggerFactory.getLogger(SingletonConfigUCassaService.class);

    @PostConstruct
    private void init() {
        List<ConfigUCassaModel> config = getConfigUCassa.getConfig();

        if (config.isEmpty()) {
            for (int i = 0; i < price.size(); i++) {

                ConfigUCassaModel newConfig = new ConfigUCassaModel(shopId, key, description.get(i), price.get(i), type.get(i));
                setConfigUCassa.setConfig(newConfig);

                log.info("Set config ucassa from application.properties. Type: " + type.get(i));
            }

            log.info("Set config ucassa success");
        } else {
            log.info("Config ucassa not empty");
        }
    }

    @PreDestroy
    private void destroy() {
        deleteConfigAllUCassa.deleteConfigAll();
        log.info("Delete config ucassa success");
    }

}
