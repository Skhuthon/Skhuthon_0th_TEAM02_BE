package com.skhuthon.sweet_little_kitty.app.service.diary.friends;

import com.skhuthon.sweet_little_kitty.app.dto.diary.DiaryDto;
import com.skhuthon.sweet_little_kitty.app.entity.diary.Diary;
import com.skhuthon.sweet_little_kitty.app.repository.diary.DiaryRepository;
import com.skhuthon.sweet_little_kitty.global.exception.CustomException;
import com.skhuthon.sweet_little_kitty.global.exception.code.ErrorCode;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsDiaryServiceImpl implements FriendsDiaryService {

    private final DiaryRepository diaryRepository;

    @Transactional(readOnly = true)
    @Override
    public ApiResponseTemplate<List<DiaryDto>> getAllFriendsDiaries() {
        List<Diary> diaries = diaryRepository.findAllPublicDiaries();
        List<DiaryDto> diaryDtos = diaries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ApiResponseTemplate.<List<DiaryDto>>builder()
                .status(200)
                .success(true)
                .message("모든 친구의 일기 조회 성공")
                .data(diaryDtos)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ApiResponseTemplate<List<DiaryDto>> getFriendDiaries(Long friendId) {
        List<Diary> diaries = diaryRepository.findPublicDiariesByFriendId(friendId);
        List<DiaryDto> diaryDtos = diaries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ApiResponseTemplate.<List<DiaryDto>>builder()
                .status(200)
                .success(true)
                .message("친구의 일기 조회 성공")
                .data(diaryDtos)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ApiResponseTemplate<DiaryDto> getFriendDiaryDetails(Long friendId, Long diaryId) {
        Diary diary = diaryRepository.findPublicDiaryByIdAndFriendId(diaryId, friendId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DIARY_EXCEPTION, "일기를 찾을 수 없습니다: " + diaryId));

        DiaryDto diaryDto = convertToDto(diary);

        return ApiResponseTemplate.<DiaryDto>builder()
                .status(200)
                .success(true)
                .message("친구의 일기 상세 조회 성공")
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
