package org.example.aideepseek.controller.subscription;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.aideepseek.annotation.swagger.controller.subscription.SubscriptionControllerAnnotationMethodSetSubscriptionUser;
import org.example.aideepseek.annotation.swagger.controller.subscription.SubscriptionControllerAnnotationMethodStartSubscription;
import org.example.aideepseek.database.service.subscription.PurchaseOfSubscription;
import org.example.aideepseek.dto.SubscriptionInfoStartDTO;
import org.example.aideepseek.database.model.TransactionSubscriptionModel;
import org.example.aideepseek.dto.SubscriptionInfoStopDTO;
import org.example.aideepseek.dto.ErrorDTO;
import org.example.aideepseek.ignite.service.subscription.GetSubscriptionInfo;
import org.example.aideepseek.ignite.service.subscription.RemoveSubscriptionInfo;
import org.example.aideepseek.ignite.service.subscription.SetSubscriptionInfo;
import org.example.aideepseek.parser_json.ParserJsonStopSubscriptionService;
import org.example.aideepseek.security.repositories.UserRepository;
import org.example.aideepseek.security.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Подписка пользователя", description = "Интеграция с юкассой")
public class SubscriptionController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseOfSubscription purchaseOfSubscription;
    @Autowired
    private SetSubscriptionInfo setSubscriptionInfo;
    @Autowired
    private GetSubscriptionInfo getSubscriptionInfo;
    @Autowired
    private RemoveSubscriptionInfo removeSubscriptionInfo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ParserJsonStopSubscriptionService parserJsonStopSubscriptionService;

    private static final ErrorDTO errorDto = new ErrorDTO();

    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    @SubscriptionControllerAnnotationMethodStartSubscription
    @PostMapping("/subscription/online/start")
    public ResponseEntity<?> startSubscription(@Valid @RequestBody SubscriptionInfoStartDTO subscriptionInfoStartDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            errorDto.setListError(bindingResult.getAllErrors());
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }

        String username = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            username = jwtUtil.extractUsername(authHeader.substring(7));
        }

        subscriptionInfoStartDto.setUsername(username);

        setSubscriptionInfo.setSubscriptionInfo(subscriptionInfoStartDto.getId(), subscriptionInfoStartDto);

        log.debug("Start subscription " + subscriptionInfoStartDto);

        return ResponseEntity.ok().build();
    }


    @SubscriptionControllerAnnotationMethodSetSubscriptionUser
    @PostMapping("/subscription/online/end")
    public ResponseEntity setSubscriptionUser(@RequestBody String requestBody) {
        SubscriptionInfoStopDTO subscriptionInfoStopDto = parserJsonStopSubscriptionService.parseNotification(requestBody);
        log.debug("Body:" + subscriptionInfoStopDto.toString());

        SubscriptionInfoStartDTO subscriptionInfoStartDto = getSubscriptionInfo.getSubscriptionInfo(subscriptionInfoStopDto.getId());
        if (subscriptionInfoStartDto != null && subscriptionInfoStartDto.getValue() == subscriptionInfoStopDto.getValue()) {

            if ("subscription".equals(subscriptionInfoStartDto.getType())) {

                log.debug("Case: subscription");

                purchaseOfSubscription
                        .purchaseOfSubscription(
                                new TransactionSubscriptionModel(
                                        userRepository.findFirstByEmail(
                                                subscriptionInfoStartDto.getUsername()),
                                        subscriptionInfoStopDto.getValue()
                                ),
                                true,
                                0
                        );

            } else if (subscriptionInfoStartDto.getType().startsWith("attempt;")) {

                String numberPart = subscriptionInfoStartDto.getType().substring("attempt;".length());

                try {
                    int attempt = Integer.parseInt(numberPart);
                    log.debug("Case: attempt = " + attempt);



                    purchaseOfSubscription
                            .purchaseOfSubscription(
                                    new TransactionSubscriptionModel(
                                            userRepository.findFirstByEmail(
                                                    subscriptionInfoStartDto.getUsername()),
                                            subscriptionInfoStopDto.getValue()
                                    ),
                                    false,
                                    attempt
                            );

                } catch (NumberFormatException e) {
                    log.error("Invalid number format in: " + subscriptionInfoStartDto.getType());
                    return ResponseEntity.status(400).build();
                }
            }else {
                log.error("Case: invalid format 'type'");
                return ResponseEntity.status(400).build();
            }

            removeSubscriptionInfo.removeSubscriptionInfo(subscriptionInfoStopDto.getId());

            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(404).build();
        }
    }
}
