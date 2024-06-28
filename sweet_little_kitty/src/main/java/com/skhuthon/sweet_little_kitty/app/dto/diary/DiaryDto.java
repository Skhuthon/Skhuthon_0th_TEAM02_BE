package com.skhuthon.sweet_little_kitty.app.dto.diary;

import com.skhuthon.sweet_little_kitty.app.entity.diary.Visibility;
import com.skhuthon.sweet_little_kitty.app.entity.region.RegionCategory;

import java.util.List;

public record DiaryDto(
        Long id,
        String title,
        String content,
        Visibility visibility,
        RegionCategory region,
        List<String> imageUrls
) {
}
