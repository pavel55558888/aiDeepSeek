package org.example.aideepseek.controller.ucassa;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.aideepseek.annotation.swagger.controller.ucassa.ConfigUcassaControllerAnnotation;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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

    @ConfigUcassaControllerAnnotation
    @GetMapping("/success")
    public ResponseEntity<String> handleSuccessGet() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:templates/success.html");

        if (!resource.exists()) {
            return ResponseEntity.status(500).body("Файл не найден");
        }

        return ResponseEntity.ok().body(Files.readString(Path.of(resource.getURI())));
    }
}
