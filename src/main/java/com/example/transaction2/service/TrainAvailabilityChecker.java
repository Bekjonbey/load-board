package com.example.transaction2.service;

import com.example.transaction2.payload.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainAvailabilityChecker {

    private final RestTemplate restTemplate = new RestTemplate();
    private final TelegramBotSender telegramBotSender;

    public TrainAvailabilityChecker(TelegramBotSender telegramBotSender) {
        this.telegramBotSender = telegramBotSender;
    }

//    @Scheduled(fixedRate = 30000) // Runs every 60 seconds
    public void checkTrainAvailability() {
        String url = "https://eticket.railway.uz/api/v3/trains/availability/space/between/stations";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Accept-Language", "uz");
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJyb2xlIjoiVVNFUiIsImlkIjoiZjRiMDdkYzEtYTAzNC00OGE2LTg4YjgtZjA2YzlkMWI1ZjYwIiwic3ViIjoidGVsZWdyYW1fMTMyNDk3NjMxNyIsImlhdCI6MTczNTAzNTc0MCwiZXhwIjoxNzM1MDM5MzQwfQ.H5a0HP-cs4RQcCvy6tmcutL0iyZrRJj3Rs-Gj4b_ztlnB6me-iIDSUgO6XE4jUyb");
        headers.add("Connection", "keep-alive");
        headers.add("Content-Type", "application/json");
        headers.add("Cookie", "_ga=GA1.1.1346669136.1729834583; G_ENABLED_IDPS=google; __stripe_mid=f694abaa-afef-4a89-aa3f-dd7cbeecd0c0998480; __stripe_sid=3bc3c323-8635-4180-921f-c3e0a707a6770af374; XSRF-TOKEN=5dac5e0a-85dc-4dfd-922f-1c30938ea2fc; _ga_K4H2SZ7MWK=GS1.1.1735035716.7.1.1735037952.0.0.0");
        headers.add("Origin", "https://eticket.railway.uz");
        headers.add("Referer", "https://eticket.railway.uz/uz/pages/trains-page");
        headers.add("Sec-Fetch-Dest", "empty");
        headers.add("Sec-Fetch-Mode", "cors");
        headers.add("Sec-Fetch-Site", "same-origin");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        headers.add("X-XSRF-TOKEN", "5dac5e0a-85dc-4dfd-922f-1c30938ea2fc");
        headers.add("device-type", "BROWSER");
        headers.add("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("sec-ch-ua-platform", "Windows");

        // Set body
        Map<String, Object> body = new HashMap<>();
        body.put("direction", new Object[]{
                Map.of(
                        "depDate", "26.12.2024",
                        "fullday", true,
                        "type", "Forward"
                )
        });
        body.put("stationFrom", "2900000");
        body.put("stationTo", "2900680");
        body.put("detailNumPlaces", 1);
        body.put("showWithoutPlaces", 0);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            String res = "";
            int i = 0;
            ArrayList<Car> cars = new ArrayList<>();
            ArrayList<String> seats = new ArrayList<>();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            ResTrain trainResponse = new ObjectMapper().readValue(response.getBody(), ResTrain.class);
            if (trainResponse != null) {
                for (Direction direction : trainResponse.getExpress().getDirection()) {
                    if (direction != null) {
                        for (Train train : direction.getTrains()) {
                            if (train != null) {
                                for (TrainDetails trainDetails : train.getTrain()) {
                                    if (trainDetails != null) {
                                        for (Car car : trainDetails.getPlaces().getCars()) {
                                            if (car != null) {
                                                if (Long.parseLong(car.getFreeSeats()) >= 1) {
                                                    cars.add(car);
                                                    res = i + "-poyezd: " + trainDetails.getDepartureTrain().getDate() + " kuni " + car.getType()+" " + car.getFreeSeats() + " ta bilet bor, Maftuna";
                                                    seats.add(res);
                                                    i++;
                                                    res="";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!cars.isEmpty())
                telegramBotSender.sendMessageToGroup("", seats.toString());
        } catch (Exception e) {
            System.err.println("Error occurred while making API request: " + e.getMessage());
        }
    }
}
