package com.example.transaction2.telegramBot.sevice;

import com.example.transaction2.telegramBot.component.TelegramUpdateHandle;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final TelegramUpdateHandle botConfig;


    @Scheduled(cron = "0 * * * * *")
    public void sendPhotoAtFixedTime() {
        try {
            SendPhoto sendPhotoRequest = new SendPhoto();
            String chatId = "-1001866020130";
            sendPhotoRequest.setChatId(chatId);
            DayOfWeek today = LocalDate.now().getDayOfWeek();
            InputFile photo = new InputFile((new File(getTodaysSchedule(today))));
            sendPhotoRequest.setPhoto(photo);

            sendPhotoRequest.setCaption("Bugungi dars jadvali");

            botConfig.execute(sendPhotoRequest);

            SendPoll sendPollRequest = new SendPoll();
            sendPollRequest.setChatId(chatId);
            sendPollRequest.setQuestion("Dars boshlanishiga yarim soat qoldi");
            sendPollRequest.setOptions(List.of("Kech qolaman", "Vaqtida boraman(galstuk bilan)"));
            sendPollRequest.setIsAnonymous(false);

            botConfig.execute(sendPollRequest);

            System.out.println("Photo and Poll sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTodaysSchedule(DayOfWeek today) {
        return switch (today) {
            case MONDAY -> "src/main/resources/static/monday.png";
            case TUESDAY -> "src/main/resources/static/tuesday.png";
            case WEDNESDAY -> "src/main/resources/static/wednesday.png";
            case THURSDAY -> "src/main/resources/static/thursday.png";
            case FRIDAY -> "src/main/resources/static/friday.png";
            default -> "";
        };
    }
}
