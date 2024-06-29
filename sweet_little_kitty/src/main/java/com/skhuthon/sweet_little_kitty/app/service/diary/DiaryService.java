package com.skhuthon.sweet_little_kitty.app.service.diary;

import com.skhuthon.sweet_little_kitty.app.dto.diary.request.DiaryRegisterReqDto;
import com.skhuthon.sweet_little_kitty.app.dto.diary.response.DiaryRegisterResDto;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DiaryService {
    ApiResponseTemplate<DiaryRegisterResDto> registerDiary(String userName, DiaryRegisterReqDto reqDto, List<MultipartFile> images);
    ApiResponseTemplate<Void> deleteDiary(String userName, Long diaryId);

}
