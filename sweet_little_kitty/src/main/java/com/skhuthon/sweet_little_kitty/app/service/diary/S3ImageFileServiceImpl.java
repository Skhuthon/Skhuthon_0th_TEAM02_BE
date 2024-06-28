package com.skhuthon.sweet_little_kitty.app.service.diary;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.skhuthon.sweet_little_kitty.global.exception.CustomException;
import com.skhuthon.sweet_little_kitty.global.exception.code.ErrorCode;
import com.skhuthon.sweet_little_kitty.global.util.GetS3Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class S3ImageFileServiceImpl implements S3ImageFileService{

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public GetS3Resource uploadSingleImageFile(MultipartFile imageFile, String directory) throws IOException {
        String imageFileName = createImageFileName(imageFile.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getSize());

        try (InputStream inputStream = imageFile.getInputStream()){
            amazonS3Client.putObject(new PutObjectRequest(bucket, imageFileName, inputStream, metadata));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FAILED_UPLOAD_IMAGE_FILE_EXCEPTION,
                    ErrorCode.FAILED_UPLOAD_IMAGE_FILE_EXCEPTION.getMessage());
        }

        return new GetS3Resource(amazonS3Client.getUrl(bucket, directory + "/" + imageFileName).toString(), imageFileName);
    }

    @Override
    public List<GetS3Resource> uploadImageFiles(List<MultipartFile> imageFiles, String directory) {
        return imageFiles.stream()
                .map(file -> {
                    try {
                        return uploadSingleImageFile(file, directory);
                    } catch (IOException e) {
                        throw new CustomException(ErrorCode.FAILED_UPLOAD_IMAGE_FILE_EXCEPTION,
                                ErrorCode.FAILED_UPLOAD_IMAGE_FILE_EXCEPTION.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    private String createImageFileName(String imageFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(imageFileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE_EXCEPTION,
                    ErrorCode.INVALID_FILE_TYPE_EXCEPTION.getMessage() + "파일명: "+ fileName);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
