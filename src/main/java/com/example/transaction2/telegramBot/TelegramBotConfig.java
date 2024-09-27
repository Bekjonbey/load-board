package com.example.transaction2.telegramBot;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
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
            try {
                Long userId = Long.parseLong(update.getMessage().getText());
                String response = String.valueOf(botService.findGroupByUser(userId));
                sendMessage(update.getMessage().getChatId().toString(), response);
            } catch (NumberFormatException e) {
                sendMessage(update.getMessage().getChatId().toString(), "Please enter a valid user ID.");
            }
        }

        if (update.hasMyChatMember()) {
            ChatMemberUpdated myChatMemberUpdate = update.getMyChatMember();
            Long groupId = myChatMemberUpdate.getChat().getId();
            String groupName = myChatMemberUpdate.getChat().getUserName();
            String newStatus = myChatMemberUpdate.getNewChatMember().getStatus();

            Long addedByUserId = myChatMemberUpdate.getFrom().getId();

            if ("member".equals(newStatus)) {
                botService.handleBotAddedToGroup(groupId, groupName, addedByUserId);
            } else if ("kicked".equals(newStatus) || "left".equals(newStatus)) {
                botService.handleBotRemovedFromGroup(groupId);
            }
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
