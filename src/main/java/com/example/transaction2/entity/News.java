package com.example.transaction2.entity;

import com.example.transaction2.payload.ContentType;
import com.example.transaction2.payload.NewsCreateDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Lob
    private byte[] photo;
    private Long viewCount = new Random().nextLong(301) + 200;
    @CreationTimestamp
    private LocalDateTime created_at;

    public News(NewsCreateDto loadAddDTO, MultipartFile photo) throws IOException {
        this.titleUz = loadAddDTO.getTitleUz();
        this.titleRu = loadAddDTO.getTitleRu();
        this.titleEng = loadAddDTO.getTitleEng();
        this.descriptionUz = loadAddDTO.getDescriptionUz();
        this.descriptionRu = loadAddDTO.getDescriptionRu();
        this.descriptionEng = loadAddDTO.getDescriptionUz();
        this.startAmount = loadAddDTO.getStartAmount();
        this.countPeople = loadAddDTO.getCountPeople();
        this.country = loadAddDTO.getCountry();
        this.period = loadAddDTO.getPeriod();
        this.contentType = loadAddDTO.getContentType();
        this.page = loadAddDTO.getPage();
        this.locationType = loadAddDTO.getLocationType();
        this.photo = photo.getBytes();
    }
}
