package com.example.transaction2.controller;

import com.example.transaction2.payload.GetNewsFilterParam;
import com.example.transaction2.payload.NewsCreateDto;
import com.example.transaction2.payload.NewsDto;
import com.example.transaction2.payload.StringResponse;
import com.example.transaction2.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/load")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping("/add")
    public StringResponse addNews(@RequestHeader("key") UUID key, @RequestParam NewsCreateDto loadAddDTO,
                                  @RequestParam("photo") MultipartFile photo) {
        return newsService.add(loadAddDTO,key,photo);
    }

    @PostMapping("/all")
    public List<NewsDto> getAll(@RequestBody GetNewsFilterParam request) {
        return newsService.getAll(request);
    }

    @GetMapping("/{id}")
    public NewsDto get(@PathVariable Long id) {
        return newsService.get(id);
    }

    @DeleteMapping("/{id}")
    public StringResponse delete(@PathVariable Long id) {
        return newsService.delete(id);
    }

}
