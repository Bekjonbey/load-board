package com.example.transaction2.payload;

import com.example.transaction2.entity.LocationType;
import com.example.transaction2.entity.News;
import com.example.transaction2.entity.PageEnum;
import lombok.Data;

import java.util.Base64;

@Data
public class NewsDto {
    private String titleUz;
    private String titleRu;
    private String titleEng;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEng;
    private PageEnum page;
    private String photo;
    private LocationType locationType;

    public NewsDto(News news) {
        this.titleUz = news.getTitleUz();
        this.descriptionUz = news.getDescriptionUz();
        this.page = news.getPage();
        this.locationType = news.getLocationType();
        this.photo = Base64.getEncoder().encodeToString(news.getPhoto());
    }
}
