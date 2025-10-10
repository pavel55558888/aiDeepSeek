package org.example.aideepseek.singleton_config_ucassa;


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
import java.util.Objects;

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
    private static final Logger log = LoggerFactory.getLogger(SingletonConfigUCassaService.class);

    @PostConstruct
    private void init() {

        if (shopId <= 0
                || key == null || key.isEmpty()
                || price == null || price.isEmpty()
                || description == null || description.isEmpty()
                || type == null || type.isEmpty()
                || price.size() != description.size()
                || price.size() != type.size()
                || price.stream().anyMatch(Objects::isNull)
                || description.stream().anyMatch(s -> s == null || s.isEmpty())
                || type.stream().anyMatch(s -> s == null || s.isEmpty())) {

            log.error("Invalid ucassa configuration: \nshopId={} \nkey='{}' \nprice={} size={} \ndescription={} size={} \ntype={} size={}",
                    shopId,
                    key,
                    price,
                    Objects.requireNonNull(price).size(),
                    description,
                    Objects.requireNonNull(description).size(),
                    type,
                    Objects.requireNonNull(type).size());

            throw new IllegalStateException("ucassa configuration is invalid or incomplete");
        }else {

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
    }

    @PreDestroy
    private void destroy() {
        deleteConfigAllUCassa.deleteConfigAll();
        log.info("Delete config ucassa success");
    }

}
