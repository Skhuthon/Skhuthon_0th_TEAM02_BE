package com.skhuthon.sweet_little_kitty.app.controller.friend;

import com.skhuthon.sweet_little_kitty.app.dto.friend.response.FriendResponseDto;
import com.skhuthon.sweet_little_kitty.app.service.friend.FriendService;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "친구 관리", description = "친구 추가, 목록 조회, 삭제를 담당하는 API 그룹")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/add")
    @Operation(summary = "친구 추가", description = "친구의 이메일을 입력하여 친구를 추가합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "친구 추가 성공"),
            @ApiResponse(responseCode = "404", description = "친구 추가 실패")
    })
    public ResponseEntity<ApiResponseTemplate<Void>> addFriend(
            @RequestParam String friendEmail, Authentication authentication) {

        ApiResponseTemplate<Void> data = friendService.addFriend(friendEmail, authentication);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping
    @Operation(summary = "친구 목록 조회", description = "현재 사용자의 친구 목록을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "친구 목록 조회 성공")
    })
    public ResponseEntity<ApiResponseTemplate<List<FriendResponseDto>>> listFriends(Authentication authentication) {

        ApiResponseTemplate<List<FriendResponseDto>> data = friendService.listFriends(authentication);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @DeleteMapping
    @Operation(summary = "친구 삭제", description = "친구를 삭제합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "친구 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "친구 삭제 실패")
    })
    public ResponseEntity<ApiResponseTemplate<Void>> deleteFriend(@RequestParam Long friendId, Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());
        ApiResponseTemplate<Void> data = friendService.deleteFriend(friendId, userId);

        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
