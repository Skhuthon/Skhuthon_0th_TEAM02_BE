package com.skhuthon.sweet_little_kitty.app.controller.friend;

import com.skhuthon.sweet_little_kitty.app.dto.friend.request.FriendRequestDto;
import com.skhuthon.sweet_little_kitty.app.dto.friend.response.FriendResponseDto;
import com.skhuthon.sweet_little_kitty.app.service.friend.FriendService;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import com.skhuthon.sweet_little_kitty.global.exception.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
@Tag(name = "친구 관리", description = "친구 추가, 목록 조회, 삭제를 담당하는 API 그룹")
public class FriendController {

    private final FriendService friendService;

    @PostMapping
    @Operation(summary = "친구 추가", description = "친구의 이메일을 입력하여 친구를 추가합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "친구 추가 성공"),
            @ApiResponse(responseCode = "404", description = "친구 추가 실패")
    })
    public ResponseEntity<ApiResponseTemplate<Void>> addFriend(@RequestBody FriendRequestDto friendRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.valueOf(userDetails.getUsername());
        ApiResponseTemplate<Void> response = friendService.addFriend(friendRequestDto, userId);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @Operation(summary = "친구 목록 조회", description = "현재 사용자의 친구 목록을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "친구 목록 조회 성공")
    })
    public ResponseEntity<ApiResponseTemplate<List<FriendResponseDto>>> listFriends(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.valueOf(userDetails.getUsername());
        ApiResponseTemplate<List<FriendResponseDto>> response = friendService.listFriends(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{friendId}")
    @Operation(summary = "친구 삭제", description = "친구를 삭제합니다.", responses = {
            @ApiResponse(responseCode = "204", description = "친구 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "친구 삭제 실패")
    })
    public ResponseEntity<ApiResponseTemplate<Void>> deleteFriend(@PathVariable Long friendId, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.valueOf(userDetails.getUsername());
        ApiResponseTemplate<Void> response = friendService.deleteFriend(friendId, userId);
        return ResponseEntity.status(204).body(response);
    }
}
