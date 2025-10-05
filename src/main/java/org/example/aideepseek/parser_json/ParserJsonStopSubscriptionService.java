package org.example.aideepseek.parser_json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aideepseek.dto.SubscriptionInfoStopDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Service
public class ParserJsonStopSubscriptionService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public SubscriptionInfoStopDTO parseNotification(String notificationJson) {
        try {
            JsonNode rootNode = objectMapper.readTree(notificationJson);
            JsonNode objectNode = rootNode.path("object");

            String idStr = objectNode.path("id").asText();
            String valueStr = objectNode.path("amount").path("value").asText();
            String createdAtStr = objectNode.path("created_at").asText();

            UUID id = UUID.fromString(idStr);
            double value = Double.parseDouble(valueStr);
            Instant createdAt = Instant.parse(createdAtStr);

            return new SubscriptionInfoStopDTO(id, value, createdAt);

        } catch (IllegalArgumentException | DateTimeParseException e) {
            throw new IllegalArgumentException("Incorrect data format in the body", e);
        } catch (Exception e) {
            throw new RuntimeException("Error when parsing the body", e);
        }
    }
}
