package com.skhuthon.sweet_little_kitty.app.service.diary;

import com.skhuthon.sweet_little_kitty.app.dto.diary.DiaryDto;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;

import java.util.List;

public interface DiaryDisplayService {

    ApiResponseTemplate<List<DiaryDto>> getAllDiaries();
    ApiResponseTemplate<List<DiaryDto>> getDiariesByRegion(String region);
    ApiResponseTemplate<DiaryDto> getDiaryById(Long diaryId);
}
