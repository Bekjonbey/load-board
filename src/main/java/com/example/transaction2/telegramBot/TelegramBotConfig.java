package com.example.transaction2.telegramBot;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotConfig extends TelegramLongPollingBot {

    private final BotService botService;

    public TelegramBotConfig(@Lazy BotService botService) {
        this.botService = botService;
    }

    @Override
    public String getBotUsername() {
        return "musy_vadakanalBot";
    }

    @Override
    public String getBotToken() {
        return "8097379815:AAGZdOC5j4LquTw7LK6q2vKFIIHXOA6NZxQ";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long messageText = Long.valueOf(update.getMessage().getText());
            String chatId = update.getMessage().getChatId().toString();

            String response = botService.findGroupByUser(messageText);  // Search for the user
            sendMessage(chatId, response);
        }
    }

    public void sendMessage(String chatId, String text) {
        try {
            execute(new org.telegram.telegrambots.meta.api.methods.send.SendMessage(chatId, text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
