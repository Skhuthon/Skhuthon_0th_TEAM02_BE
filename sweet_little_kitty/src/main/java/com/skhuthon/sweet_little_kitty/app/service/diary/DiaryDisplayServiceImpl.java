package com.skhuthon.sweet_little_kitty.app.service.diary;

import com.skhuthon.sweet_little_kitty.app.dto.diary.DiaryDto;
import com.skhuthon.sweet_little_kitty.app.entity.diary.Diary;
import com.skhuthon.sweet_little_kitty.app.entity.region.RegionCategory;
import com.skhuthon.sweet_little_kitty.app.repository.diary.DiaryRepository;
import com.skhuthon.sweet_little_kitty.app.repository.region.RegionRepository;
import com.skhuthon.sweet_little_kitty.global.exception.CustomException;
import com.skhuthon.sweet_little_kitty.global.exception.code.ErrorCode;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryDisplayServiceImpl implements DiaryDisplayService {

    private final DiaryRepository diaryRepository;
    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<DiaryDto>> getAllDiaries() {
        List<Diary> diaries = diaryRepository.findAll();
        List<DiaryDto> diaryDtos = diaries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ApiResponseTemplate.<List<DiaryDto>>builder()
                .status(200)
                .success(true)
                .message("모든 일기 조회 성공")
                .data(diaryDtos)
                .build();
    }

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<DiaryDto>> getDiariesByRegion(String region) {
        RegionCategory regionCategory = RegionCategory.convertToCategory(region);
        List<Diary> diaries = regionRepository.findByCategory(regionCategory)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DIARY_EXCEPTION, "해당 지역의 일기를 찾을 수 없습니다: " + regionCategory))
                .getDiaries();

        List<DiaryDto> diaryDtos = diaries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ApiResponseTemplate.<List<DiaryDto>>builder()
                .status(200)
                .success(true)
                .message(regionCategory.getDisplayName() + " 지역의 일기 조회 성공")
                .data(diaryDtos)
                .build();
    }

    @Transactional(readOnly = true)
    public ApiResponseTemplate<DiaryDto> getDiaryById(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DIARY_EXCEPTION, "일기를 찾을 수 없습니다: " + diaryId));
        DiaryDto diaryDto = convertToDto(diary);

        return ApiResponseTemplate.<DiaryDto>builder()
                .status(200)
                .success(true)
                .message("일기 상세 조회 성공")
                .data(diaryDto)
                .build();
    }

    private DiaryDto convertToDto(Diary diary) {
        return new DiaryDto(
                diary.getDiaryId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getVisibility(),
                diary.getRegion().getCategory(),
                diary.getImageUrls()
        );
    }
}
