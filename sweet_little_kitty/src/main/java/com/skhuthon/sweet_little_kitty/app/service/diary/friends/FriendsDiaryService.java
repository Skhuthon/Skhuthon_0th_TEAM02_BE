package com.skhuthon.sweet_little_kitty.app.service.diary.friends;

import com.skhuthon.sweet_little_kitty.app.dto.diary.DiaryDto;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FriendsDiaryService {
    ApiResponseTemplate<List<DiaryDto>> getAllFriendsDiaries(Authentication authentication);
    ApiResponseTemplate<List<DiaryDto>> getFriendDiaries(Long friendId, Authentication authentication);
    ApiResponseTemplate<DiaryDto> getFriendDiaryDetails(Long friendId, Long diaryId, Authentication authentication);
}
