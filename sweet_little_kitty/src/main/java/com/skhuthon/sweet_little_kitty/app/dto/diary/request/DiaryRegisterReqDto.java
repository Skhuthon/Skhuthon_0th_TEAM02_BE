package com.skhuthon.sweet_little_kitty.app.dto.diary.request;

import com.skhuthon.sweet_little_kitty.app.entity.diary.Visibility;
import com.skhuthon.sweet_little_kitty.app.entity.region.RegionCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record DiaryRegisterReqDto(
        String title,
        String content,
        Visibility visibility,
        RegionCategory region
) {
}
