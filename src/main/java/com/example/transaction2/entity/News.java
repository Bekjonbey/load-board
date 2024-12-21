package com.example.transaction2.entity;

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
    private PageEnum page;
    private LocationType locationType;
    @Lob
    private byte[] photo;
    private Long viewCount = new Random().nextLong(301) + 200;
    @CreationTimestamp
    private LocalDateTime created_at;

    public News(NewsCreateDto loadAddDTO, MultipartFile photo) throws IOException {
        this.titleUz = loadAddDTO.getTitleUz();
        this.descriptionUz = loadAddDTO.getDescriptionUz();
        this.page = loadAddDTO.getPage();
        this.locationType = loadAddDTO.getLocationType();
        this.photo = photo.getBytes();
    }
}
