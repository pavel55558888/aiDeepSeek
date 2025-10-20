package org.example.aideepseek.controller.ucassa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.aideepseek.database.model.ConfigUCassaModel;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController()
@Tag(name = "Просто прикольная html страница", description = "После оплаты пользователя переводит на нее")
public class UCassaSuccessController {

    private final ResourceLoader resourceLoader;

    public UCassaSuccessController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Operation(summary = "Возвращает прикольную html страницу")
    @ApiResponse(responseCode = "200", description = "Успешный ответ")
    @ApiResponse(responseCode = "500", description = "Файл не найден")
    @GetMapping("/success")
    public ResponseEntity<String> handleSuccessGet() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:templates/success.html");

        if (!resource.exists()) {
            return ResponseEntity.status(500).body("Файл не найден");
        }

        return ResponseEntity.ok().body(Files.readString(Path.of(resource.getURI())));
    }
}
