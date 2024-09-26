package com.example.transaction2.telegramBot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotInitializer {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramLongPollingBot telegramBot) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            // Register your bot here, passing your bot class bean
            botsApi.registerBot(telegramBot);
            return botsApi;
        } catch (TelegramApiException e) {
            throw new RuntimeException("Error initializing Telegram bot", e);
        }
    }
}
