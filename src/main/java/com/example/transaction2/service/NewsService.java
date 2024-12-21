package com.example.transaction2.service;

import com.example.transaction2.entity.News;
import com.example.transaction2.payload.GetNewsFilterParam;
import com.example.transaction2.payload.NewsCreateDto;
import com.example.transaction2.payload.NewsDto;
import com.example.transaction2.payload.StringResponse;
import com.example.transaction2.repository.AdminRepository;
import com.example.transaction2.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import javax.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepository newsRepository;
    private final AdminRepository adminRepository;

    public StringResponse add(NewsCreateDto loadAddDTO, UUID key, MultipartFile photo) {
        try {
            if (adminRepository.existsByKey(key)) {
                newsRepository.save(new News(loadAddDTO, photo));
            } else {
                return new StringResponse("key not found");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new StringResponse("success");
    }

    public List<NewsDto> getAll(GetNewsFilterParam request) {
        return newsRepository.findAll(getNewsSpecification(request))
                .stream()
                .map(NewsDto::new)
                .toList();
    }

    public NewsDto get(Long id) {
        return new NewsDto(newsRepository.findById(id).get());
    }

    private Specification<News> getNewsSpecification(GetNewsFilterParam request) {
        return (root, query, criteriaBuilder) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getPage())) {
                predicates.add(criteriaBuilder.equal(root.get("page"), request.getPage()));
            }

            if (Objects.nonNull(request.getLocationType())) {
                predicates.add(criteriaBuilder.equal(root.get("locationType"), request.getLocationType()));
            }

            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }

    public StringResponse delete(Long id) {
        newsRepository.deleteById(id);
        return new StringResponse("success");
    }
}
