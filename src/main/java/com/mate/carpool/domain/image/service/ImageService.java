package com.mate.carpool.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mate.carpool.shared.exception.CustomHttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final AmazonS3Client client;

    @Value("${aws.bucket.name}")
    private String bucketName;
    @Value("${car.default.url}")
    private String DEFAULT_CAR_IMAGE_URL;

    /**
     * 이미지 업로드를 위한 메소드
     */
    public String upload(MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()) return DEFAULT_CAR_IMAGE_URL;
        if (!isImage(file.getOriginalFilename())) {
            throw new CustomHttpException(HttpStatus.BAD_REQUEST, "확장자가 png, jpg, jpeg 경우만 이미지를 업로드 하실 수 있습니다.");
        }

        String newImageName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // image aws s3 upload
        client.putObject(
                new PutObjectRequest(bucketName, newImageName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return client.getUrl(bucketName, newImageName).toString();
    }

    private boolean isImage(String fileName) {
        if (fileName == null) return false;
        fileName = fileName.toLowerCase();
        return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
    }
}
