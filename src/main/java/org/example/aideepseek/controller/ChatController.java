package org.example.aideepseek.controller;


import org.example.aideepseek.deepseek.service.DeepSeekService;
import org.example.aideepseek.ignite.service.task.GetTask;
import org.example.aideepseek.ignite.service.task.SetCacheTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/v1")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private DeepSeekService deepSeekService;
    @Autowired
    private GetTask getTask;
    @Autowired
    private SetCacheTask setCacheTask;

    @PostMapping
    public ResponseEntity<String> chat(
            @RequestBody String userMessage,
            @RequestHeader(value = "format") String format){
        log.debug("format req: " + format);
        log.debug(userMessage);
        String response = "";

        try {
            if (format.equals("html")) {
                var taskCache = getTask.getTask(userMessage);

                if (taskCache != null) {
                    return ResponseEntity.ok(taskCache);
                }


                String question = "";
                String answer = "";

                //далее отправляем в дипсик и получаем верный ответ
                response = deepSeekService.getChatCompletion(question + answer + ". Верни только верный ответ на вопрос без лишних символов и букв");


                setCacheTask.setCacheTask(question, response);
            } else if (format.equals("question")) {
                response = deepSeekService.getChatCompletion(userMessage);
            } else {
                return ResponseEntity.status(500).build();
            }

            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}