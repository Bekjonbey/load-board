package com.example.transaction2.telegramBot.component;

import com.example.transaction2.telegramBot.entity.Group;
import com.example.transaction2.telegramBot.repository.GroupRepository;
import com.example.transaction2.telegramBot.sevice.BotService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TelegramUpdateHandle extends TelegramLongPollingBot {

    private final BotService botService;
    private final GroupRepository groupRepository;

    public TelegramUpdateHandle(@Lazy BotService botService, GroupRepository groupRepository) {
        this.botService = botService;
        this.groupRepository = groupRepository;
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

        if (update.hasMessage()) {
            Message message = update.getMessage();
            Chat chat = message.getChat();

            if (message.getLeftChatMember() != null) {
                User leftUser = message.getLeftChatMember();
                String chatId = chat.getId().toString();
                String userId = leftUser.getId().toString();

                onUserLeave(chatId, userId);
            }
            if (!message.getNewChatMembers().isEmpty()) {
                List<User> newUsers = message.getNewChatMembers();
                for (User newUser : newUsers) {
                    String chatId = chat.getId().toString();
                    String userId = newUser.getId().toString();
                    String username = chat.getUserName();

                    onUserJoin(chatId, userId, username);
                }
            }
        }

//        if (update.hasMessage() && update.getMessage().getForwardFrom() != null) {
//            try {
//                User forwardedUser = update.getMessage().getForwardFrom();
//                Long userId = forwardedUser.getId();
//                String response = String.valueOf(botService.findGroupByUser(userId));
//                sendMessage(update.getMessage().getChatId().toString(), response);
//            } catch (NumberFormatException e) {
//                sendMessage(update.getMessage().getChatId().toString(), "Please enter a valid user ID.");
//            }
//        }

//        if (update.hasMessage() && update.getMessage().hasText()) {
//            Message message = update.getMessage();
//            Chat chat = message.getChat();
//
//            String chatId = chat.getId().toString();
//            String chatType = chat.getType();
//            String userId = message.getFrom().getId().toString();
//            String username = message.getChat().getUserName();
//
//            if ("group".equals(chatType) || "supergroup".equals(chatType)) {
//                storeUser(chatId, userId, username);
//            }
//        }


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

    public void onUserLeave(String groupId, String userId) {
        Optional<Group> existingGroupMember = groupRepository.findByGroupIdAndUserId(groupId, userId);

        if (existingGroupMember.isPresent()) {
            Group group = existingGroupMember.get();
            group.setDeleted(true);
            group.setUpdatedDate(LocalDateTime.now());
            groupRepository.save(group);
        }
    }

    public void onUserJoin(String groupId, String userId, String username) {
        storeUser(groupId, userId, username);
    }


    public void storeUser(String groupId, String userId, String username) {
        Optional<Group> existingGroupMember = groupRepository.findByGroupIdAndUserId(groupId, userId);
        if (existingGroupMember.isEmpty()) {
            Group newGroupMember = new Group();
            newGroupMember.setGroupId(groupId);
            newGroupMember.setUserId(userId);
            newGroupMember.setUsername("@"+username);
            newGroupMember.setCreatedDate(LocalDateTime.now());
            newGroupMember.setUpdatedDate(LocalDateTime.now());
            newGroupMember.setDeleted(false);
            groupRepository.save(newGroupMember);
        }
        else {
            existingGroupMember.get().setDeleted(false);
            groupRepository.save(existingGroupMember.get());
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
