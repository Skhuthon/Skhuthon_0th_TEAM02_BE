package com.skhuthon.sweet_little_kitty.app.controller.diary;

import com.skhuthon.sweet_little_kitty.app.dto.diary.request.DiaryRegisterReqDto;
import com.skhuthon.sweet_little_kitty.app.dto.diary.response.DiaryRegisterResDto;
import com.skhuthon.sweet_little_kitty.app.service.diary.DiaryService;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "여행 일기 작성/삭제", description = "여행 일기를 작성/삭제하는 api 그룹")
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping(value ="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "여행 일기 작성",
            description = "여행 일기를 작성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "여행 일기 작성 성공"),
                    @ApiResponse(responseCode = "403", description = "url문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<DiaryRegisterResDto>> registerDiary(
            @RequestPart("reqDto") DiaryRegisterReqDto reqDto,
            @RequestPart("images") List<MultipartFile> images,
            Authentication authentication) {

        String userName = authentication.getName();
        ApiResponseTemplate<DiaryRegisterResDto> data = diaryService.registerDiary(userName, reqDto, images);

        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @DeleteMapping
    @Operation(
            summary = "특정 여행 일기 삭제",
            description = "특정 여행 일기를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "특정 여행 일기 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "일기를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResponseTemplate<Void>> deleteDiary(
            @RequestParam Long diaryId,
            Authentication authentication) {

        String userName = authentication.getName();
        ApiResponseTemplate<Void> data = diaryService.deleteDiary(userName, diaryId);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
