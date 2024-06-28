package com.skhuthon.sweet_little_kitty.app.service.diary;

import com.skhuthon.sweet_little_kitty.app.dto.diary.request.DiaryRegisterReqDto;
import com.skhuthon.sweet_little_kitty.app.dto.diary.response.DiaryRegisterResDto;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;

import java.io.IOException;

public interface DiaryService {
    ApiResponseTemplate<DiaryRegisterResDto> registerDiary(String userEmail, DiaryRegisterReqDto reqDto) throws IOException;
}
