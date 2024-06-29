package com.skhuthon.sweet_little_kitty.app.controller.diary.friends;

import com.skhuthon.sweet_little_kitty.app.dto.diary.DiaryDto;
import com.skhuthon.sweet_little_kitty.app.service.diary.friends.FriendsDiaryService;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diary/friends/display")
@RequiredArgsConstructor
@Tag(name = "친구 일기 조회", description = "친구의 일기를 조회하는 API 그룹")
public class FriendsDiaryController {

    private final FriendsDiaryService friendsDiaryService;

    @GetMapping
    @Operation(summary = "모든 친구의 일기 조회", description = "사용자의 모든 친구의 공개된 일기를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "일기 조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
            @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
    })
    public ResponseEntity<ApiResponseTemplate<List<DiaryDto>>> getAllFriendsDiaries() {
        ApiResponseTemplate<List<DiaryDto>> response = friendsDiaryService.getAllFriendsDiaries();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{friendId}")
    @Operation(summary = "친구의 일기 조회", description = "특정 친구의 공개된 일기를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "일기 조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
            @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
    })
    public ResponseEntity<ApiResponseTemplate<List<DiaryDto>>> getFriendDiaries(@PathVariable Long friendId) {
        ApiResponseTemplate<List<DiaryDto>> response = friendsDiaryService.getFriendDiaries(friendId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{friendId}/{diaryId}")
    @Operation(summary = "친구의 일기 상세 조회", description = "특정 친구의 특정 일기를 상세 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "일기 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회된 일기가 없음"),
            @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
    })
    public ResponseEntity<ApiResponseTemplate<DiaryDto>> getFriendDiaryDetails(@PathVariable Long friendId, @PathVariable Long diaryId) {
        ApiResponseTemplate<DiaryDto> response = friendsDiaryService.getFriendDiaryDetails(friendId, diaryId);
        return ResponseEntity.ok(response);
    }
}
