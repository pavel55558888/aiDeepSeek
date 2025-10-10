package org.example.aideepseek.parser_json;

import org.example.aideepseek.dto.SubscriptionInfoStopDTO;

public interface ParserJsonStopSubscriptionService {
    public SubscriptionInfoStopDTO parseNotification(String notificationJson);
}
