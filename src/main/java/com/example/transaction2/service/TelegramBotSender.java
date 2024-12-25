package com.example.transaction2.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramBotSender {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BOT_TOKEN = "8097379815:AAH06uoSIb7xRKQWBiPwKkSDrszhE-UIJs8"; // Replace with your bot token
    private static final String BOT_USERNAME = "YOUR_BOT_USERNAME"; // Replace with your bot username

    public void sendMessageToGroup(String groupChatId, String message) {
        String telegramApiUrl = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("chat_id", "-1001866020130");
        requestBody.put("text", message);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(telegramApiUrl, HttpMethod.POST, requestEntity, String.class);
            System.out.println("Message sent successfully: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
