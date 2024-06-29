package com.skhuthon.sweet_little_kitty.app.controller.diary;

import com.skhuthon.sweet_little_kitty.app.dto.diary.DiaryDto;
import com.skhuthon.sweet_little_kitty.app.service.diary.DiaryDisplayService;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "여행 일기 조회", description = "여행 일기를 조회하는 api 그룹")
@RequestMapping("/api/v1/diary/display")
public class DiaryDisplayController {

    private final DiaryDisplayService diaryDisplayService;

    @GetMapping
    @Operation(
            summary = "모든 여행 일기 조회",
            description = "등록된 모든 여행 일기를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "여행 일기 목록 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<List<DiaryDto>>> getAllDiaries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<DiaryDto> diaries = diaryDisplayService.getAllDiaries(authentication).getData();
        ApiResponseTemplate<List<DiaryDto>> data = ApiResponseTemplate.<List<DiaryDto>>builder()
                .status(200)
                .success(true)
                .message("모든 여행 일기 조회 성공")
                .data(diaries)
                .build();
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/region")
    @Operation(
            summary = "지역별 여행 일기 조회",
            description = "특정 지역의 여행 일기를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "지역별 여행 일기 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<List<DiaryDto>>> getDiariesByRegion(@RequestParam String region, Authentication authentication) {
        List<DiaryDto> diaries = diaryDisplayService.getDiariesByRegion(region, authentication).getData();
        ApiResponseTemplate<List<DiaryDto>> data = ApiResponseTemplate.<List<DiaryDto>>builder()
                .status(200)
                .success(true)
                .message("지역별 여행 일기 조회 성공")
                .data(diaries)
                .build();
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/{diaryId}")
    @Operation(
            summary = "특정 여행 일기 조회",
            description = "특정 여행 일기를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "특정 여행 일기 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<DiaryDto>> getDiaryById(@PathVariable Long diaryId) {
        DiaryDto diary = diaryDisplayService.getDiaryById(diaryId).getData();
        ApiResponseTemplate<DiaryDto> data = ApiResponseTemplate.<DiaryDto>builder()
                .status(200)
                .success(true)
                .message("특정 여행 일기 조회 성공")
                .data(diary)
                .build();
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}