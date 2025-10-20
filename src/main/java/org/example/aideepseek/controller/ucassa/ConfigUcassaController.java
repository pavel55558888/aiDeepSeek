package org.example.aideepseek.controller.ucassa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.aideepseek.annotation.ApiResponseUnAuth;
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

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Ассортимент подписок", description = "Предназначен для получения всех возможных вариаций подписок и информацию о магазине")
public class ConfigUcassaController {
    @Autowired
    private GetConfigUCassa getConfigUCassa;
    private static final Logger log = LoggerFactory.getLogger(ConfigUcassaController.class);

    @Operation(
            summary = "Получить список подписок",
            description = "Возвращает все возможные подписки/попытки, которые можно купить и магазин"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешный ответ",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConfigUCassaModel.class))
    )
    @ApiResponse(responseCode = "500", description = "В бд нет никаких подписок, сервер их не подгрузил, возможно в пропертя не положили их")
    @ApiResponseUnAuth
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
