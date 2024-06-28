package com.skhuthon.sweet_little_kitty.app.dto.diary.response;

import com.skhuthon.sweet_little_kitty.app.entity.diary.Visibility;
import com.skhuthon.sweet_little_kitty.app.entity.region.RegionCategory;
import lombok.Builder;

import java.util.List;

@Builder
public record DiaryRegisterResDto(
        String title,
        String content,
        Visibility visibility,
        RegionCategory region,
        List<String> imageUrls
) {
}
