package com.example.transaction2.telegramBot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Service
public class BotService {

    private final TelegramBotConfig botConfig;
    private final GroupRepository groupRepository;

    public BotService(TelegramBotConfig botConfig, GroupRepository groupRepository) {
        this.botConfig = botConfig;
        this.groupRepository = groupRepository;
    }

    public StringBuilder findGroupByUser(Long usernameOrId) {
        StringBuilder result = new StringBuilder();
        ArrayList<String> groupList = new ArrayList<>();
        for (Map.Entry<Long, String> entry : getAllGroupIds().entrySet()) {
            try {
                ChatMember member = botConfig.execute(new GetChatMember(entry.getKey().toString(), usernameOrId));
                if (member != null) {
                    groupList.add(entry.getValue());
                }
            } catch (TelegramApiException e) {
                // Continue to the next group if user not found
            }
        }
        if (!groupList.isEmpty()) {
            for (String s : groupList) {
                result.append(s).append(", ");
            }
            return result;
        }
        else
            return new StringBuilder("User not found in any groups.");
    }

    public void handleBotAddedToGroup(Long groupId, String groupName, Long addedByUserId) {
        Optional<Group> byGroupId = groupRepository.findFirstByGroupId(String.valueOf(groupId));
        if (byGroupId.isPresent()) {
            Group group = byGroupId.get();
            group.setDeleted(false);
        }
        else {
            Group group = new Group();
            group.setGroupId(String.valueOf(groupId));
            group.setUsername("@"+groupName);
            group.setUserId(String.valueOf(addedByUserId));
            groupRepository.save(group);
        }
    }

    public void handleBotRemovedFromGroup(Long groupId) {
        Optional<Group> byGroupId = groupRepository.findFirstByGroupId(String.valueOf(groupId));
        if (byGroupId.isPresent()) {
            Group group = byGroupId.get();
            group.setDeleted(true);
        }
    }

    private HashMap<Long, String> getAllGroupIds() {
        List<Group> all = groupRepository.findAllByDeletedIs(false);
        HashMap<Long, String> ids = new HashMap<>();
        for (Group group : all) {
            try {
                GetChat getChat = new GetChat(group.getUsername());
                Long chatId = botConfig.execute(getChat).getId();
                ids.put(chatId, group.getUsername());
            } catch (TelegramApiException e) {
                System.err.println("Error retrieving group ID for: " + group.getUsername() + ". " + e.getMessage());
            }
        }
        return ids;
    }
}
