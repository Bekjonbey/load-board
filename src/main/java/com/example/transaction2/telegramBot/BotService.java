package com.example.transaction2.telegramBot;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BotService {

    private final TelegramBotConfig botConfig;
    private List<Long> groupIds;

    public BotService(TelegramBotConfig botConfig) {
        this.botConfig = botConfig;
        initializeGroupIds();  // Initialize group IDs when the service is created
    }

    public String findGroupByUser(Long usernameOrId) {
        for (Long groupId : groupIds) {
            try {
                ChatMember member = botConfig.execute(new GetChatMember(groupId.toString(), usernameOrId));
                if (member != null) {
                    return "User found in group ID: " + groupId;
                }
            } catch (TelegramApiException e) {
                // User not found in this group, continue to the next one
            }
        }
        return "User not found in any groups.";
    }

    private void initializeGroupIds() {
        groupIds = retrieveGroupIds();  // Fetch group IDs at initialization
        System.out.println("Initialized group IDs: " + groupIds);
    }

    private List<Long> retrieveGroupIds() {
        List<String> groupUsernames = Arrays.asList(
                "@javaGrow"
        );

        List<Long> ids = new ArrayList<>();
        for (String username : groupUsernames) {
            try {
                Chat chat = botConfig.execute(new GetChat(username));
                ids.add(chat.getId());
            } catch (TelegramApiException e) {
                System.err.println("Error retrieving group ID for username: " + username + ". " + e.getMessage());
            }
        }
        return ids;
    }
}
