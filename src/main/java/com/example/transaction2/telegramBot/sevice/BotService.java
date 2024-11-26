package com.example.transaction2.telegramBot.sevice;

import com.example.transaction2.telegramBot.entity.Group;
import com.example.transaction2.telegramBot.repository.GroupRepository;
import com.example.transaction2.telegramBot.component.TelegramUpdateHandle;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BotService {

    private final TelegramUpdateHandle botConfig;
    private final GroupRepository groupRepository;

    public BotService(TelegramUpdateHandle botConfig, GroupRepository groupRepository) {
        this.botConfig = botConfig;
        this.groupRepository = groupRepository;
    }

    public StringBuilder findGroupByUser(Long userId) {
        StringBuilder result = new StringBuilder();
        ArrayList<String> groupList = new ArrayList<>();
        for (Map.Entry<Long, String> entry : getAllGroupIds().entrySet()) {
            try {
                ChatMember member = botConfig.execute(new GetChatMember(entry.getKey().toString(), userId));
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
            return new StringBuilder("user found in these groups: " + result);
        }
        else
            return new StringBuilder("User not found in any groups.");
    }

    public void handleBotAddedToGroup(Long groupId, String groupName, Long addedByUserId) {
        Optional<Group> byGroupId = groupRepository.findFirstByGroupId(String.valueOf(groupId));
        if (byGroupId.isPresent()) {
            Group group = byGroupId.get();
            group.setDeleted(false);
            groupRepository.save(group);
        }
        else {
            Group group = new Group();
            group.setCreatedDate(LocalDateTime.now());
            group.setUpdatedDate(LocalDateTime.now());
            group.setGroupId(String.valueOf(groupId));
            group.setUsername("@"+groupName);
            group.setUserId(String.valueOf(addedByUserId));
            group.setAddedToGroupByUser(String.valueOf(addedByUserId));
            groupRepository.save(group);
        }
    }

    public void handleBotRemovedFromGroup(Long groupId) {
        Optional<Group> byGroupId = groupRepository.findFirstByGroupId(String.valueOf(groupId));
        if (byGroupId.isPresent()) {
            Group group = byGroupId.get();
            group.setUpdatedDate(LocalDateTime.now());
            group.setDeleted(true);
            groupRepository.save(group);
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
