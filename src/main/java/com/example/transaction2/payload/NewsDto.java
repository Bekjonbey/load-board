package com.example.transaction2.payload;

import com.example.transaction2.entity.LocationType;
import com.example.transaction2.entity.News;
import com.example.transaction2.entity.PageEnum;
import lombok.Data;

import java.util.Base64;
import java.util.Objects;

@Data
public class NewsDto {
    private String title;
    private String description;
    private PageEnum page;
    private String photo;
    private LocationType locationType;
    private ContentType contentType;
    private String startAmount;
    private String countPeople;
    private String country;
    private String period;

    public NewsDto(News news, String lang) {
        this.title = getTitleByLang(news, lang);
        this.description = getDescriptionByLang(news, lang);
        this.page = news.getPage();
        this.locationType = news.getLocationType();
        this.photo = Base64.getEncoder().encodeToString(news.getPhoto());
        this.startAmount = news.getStartAmount();
        this.countPeople = news.getCountPeople();
        this.country = news.getCountry();
        this.period = news.getPeriod();
    }

    private String getDescriptionByLang(News news, String lang) {
        if (Objects.equals(lang, "en")) {
            return news.getDescriptionEng();
        } else if (Objects.equals(lang, "ru")) {
            return news.getDescriptionRu();
        } else if (Objects.equals(lang, "uz")) {
            return news.getDescriptionUz();
        } else return news.getDescriptionUz();
    }

    private String getTitleByLang(News news, String lang) {
        if (Objects.equals(lang, "en")) {
            return news.getTitleEng();
        } else if (Objects.equals(lang, "ru")) {
            return news.getTitleRu();
        } else if (Objects.equals(lang, "uz")) {
            return news.getTitleUz();
        } else return news.getTitleUz();
    }
}
