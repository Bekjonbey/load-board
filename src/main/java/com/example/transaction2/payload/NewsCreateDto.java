package com.example.transaction2.payload;

import com.example.transaction2.entity.LocationType;
import com.example.transaction2.entity.PageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class NewsCreateDto {
    private String titleUz;
    private String titleRu;
    private String titleEng;
    private String descriptionRu;
    private String descriptionEng;
    private String descriptionUz;
    private String startAmount;
    private String countPeople;
    private String country;
    private String period;
    private ContentType contentType;
    private PageEnum page;
    private LocationType locationType;
}

