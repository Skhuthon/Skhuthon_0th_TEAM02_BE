package com.skhuthon.sweet_little_kitty.app.service.diary;

import com.skhuthon.sweet_little_kitty.app.dto.diary.request.DiaryRegisterReqDto;
import com.skhuthon.sweet_little_kitty.app.dto.diary.response.DiaryRegisterResDto;
import com.skhuthon.sweet_little_kitty.app.entity.diary.Diary;
import com.skhuthon.sweet_little_kitty.app.entity.region.Region;
import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import com.skhuthon.sweet_little_kitty.app.repository.diary.DiaryRepository;
import com.skhuthon.sweet_little_kitty.app.repository.region.RegionRepository;
import com.skhuthon.sweet_little_kitty.app.repository.user.UserRepository;
import com.skhuthon.sweet_little_kitty.global.exception.CustomException;
import com.skhuthon.sweet_little_kitty.global.exception.code.ErrorCode;
import com.skhuthon.sweet_little_kitty.global.template.ApiResponseTemplate;
import com.skhuthon.sweet_little_kitty.global.util.GetS3Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final S3ImageFileService s3ImageFileService;

    @Transactional
    public ApiResponseTemplate<DiaryRegisterResDto> registerDiary(String userEmail, DiaryRegisterReqDto reqDto, List<MultipartFile> images) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "해당 이메일을 가진 사용자를 찾을 수 없습니다: " + userEmail));

        Region region = regionRepository.findByCategory(reqDto.region())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REGION_EXCEPTION, "해당 지역을 찾을 수 없습니다: " + reqDto.region()));

        List<GetS3Resource> imageUrls = uploadImages(images, "diary");

        Diary diary = Diary.builder()
                .title(reqDto.title())
                .content(reqDto.content())
                .visibility(reqDto.visibility())
                .user(user)
                .region(region)
                .imageUrls(imageUrls.stream().map(GetS3Resource::getImageUrl).collect(Collectors.toList()))
                .build();

        diaryRepository.save(diary);

        DiaryRegisterResDto resDto = DiaryRegisterResDto.builder()
                .title(diary.getTitle())
                .content(diary.getContent())
                .visibility(diary.getVisibility())
                .region(diary.getRegion().getCategory())
                .imageUrls(diary.getImageUrls())
                .build();

        return ApiResponseTemplate.<DiaryRegisterResDto>builder()
                .status(201)
                .success(true)
                .message("일기 작성 성공")
                .data(resDto)
                .build();
    }

    @Transactional
    public ApiResponseTemplate<Void> deleteDiary(String userEmail, Long diaryId) {

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DIARY_EXCEPTION, "해당 일기를 찾을 수 없습니다: " + diaryId));

        if (!diary.getUser().getEmail().equals(userEmail)) {
            throw new CustomException(ErrorCode.ONLY_OWN_DIARY_MODIFY_EXCEPTION, "본인이 작성한 일기만 삭제 가능합니다.");
        }

        diaryRepository.delete(diary);

        return ApiResponseTemplate.<Void>builder()
                .status(200)
                .success(true)
                .message("일기 삭제 성공")
                .build();
    }

    private List<GetS3Resource> uploadImages(List<MultipartFile> files, String directory) {
        return s3ImageFileService.uploadImageFiles(files, directory);
    }
}
