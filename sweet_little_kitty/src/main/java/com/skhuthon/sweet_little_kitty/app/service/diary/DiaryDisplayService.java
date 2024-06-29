package com.skhuthon.sweet_little_kitty.app.service.diary;

import com.skhuthon.sweet_little_kitty.app.dto.diary.DiaryDto;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DiaryDisplayService {

    ApiResponseTemplate<List<DiaryDto>> getAllDiaries(Authentication authentication);
    ApiResponseTemplate<List<DiaryDto>> getDiariesByRegion(String region, Authentication authentication);
    ApiResponseTemplate<DiaryDto> getDiaryById(Long diaryId);
}
