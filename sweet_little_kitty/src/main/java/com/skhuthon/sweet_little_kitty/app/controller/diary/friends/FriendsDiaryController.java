package com.skhuthon.sweet_little_kitty.app.controller.diary.friends;

import com.skhuthon.sweet_little_kitty.app.dto.diary.DiaryDto;
import com.skhuthon.sweet_little_kitty.app.service.diary.friends.FriendsDiaryService;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diary/display/friends")
@RequiredArgsConstructor
@Tag(name = "친구 일기 조회", description = "친구의 일기를 조회하는 API 그룹")
public class FriendsDiaryController {

    private final FriendsDiaryService friendsDiaryService;

    @GetMapping
    @Operation(summary = "모든 친구의 일기 조회", description = "사용자의 모든 친구의 공개된 일기를 조회합니다.",
            responses = {
            @ApiResponse(responseCode = "200", description = "일기 조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
            @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
    }
    )
    public ResponseEntity<ApiResponseTemplate<List<DiaryDto>>> getAllFriendsDiaries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<DiaryDto> diaries = friendsDiaryService.getAllFriendsDiaries(authentication).getData();
        ApiResponseTemplate<List<DiaryDto>> data = ApiResponseTemplate.<List<DiaryDto>>builder()
                .status(200)
                .success(true)
                .message("모든 친구의 일기 조회 성공")
                .data(diaries)
                .build();
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/search")
    @Operation(summary = "특정 친구의 일기 조회", description = "특정 친구의 공개된 일기를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "일기 조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
            @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
    })
    public ResponseEntity<ApiResponseTemplate<List<DiaryDto>>> getFriendDiaries(@RequestParam Long friendId, Authentication authentication) {
        List<DiaryDto> diaries = friendsDiaryService.getFriendDiaries(friendId, authentication).getData();
        ApiResponseTemplate<List<DiaryDto>> data = ApiResponseTemplate.<List<DiaryDto>>builder()
                .status(200)
                .success(true)
                .message("친구의 일기 조회 성공")
                .data(diaries)
                .build();
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/search/detail")
    @Operation(summary = "특정 친구의 일기 상세 조회", description = "특정 친구의 일기를 상세 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "일기 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
            @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
    })
    public ResponseEntity<ApiResponseTemplate<DiaryDto>> getDiaryById(@RequestParam Long friendId, @RequestParam Long diaryId, Authentication authentication) {
        DiaryDto diary = friendsDiaryService.getFriendDiaryDetails(friendId, diaryId, authentication).getData();
        ApiResponseTemplate<DiaryDto> data = ApiResponseTemplate.<DiaryDto>builder()
                .status(200)
                .success(true)
                .message("특정 친구의 일기 상세 조회 성공")
                .data(diary)
                .build();
        return ResponseEntity.status(data.getStatus()).body(data);
    }

}
