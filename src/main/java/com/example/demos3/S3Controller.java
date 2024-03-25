package com.example.demos3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final AmazonS3 amazonS3;
    private final AwsS3Config awsS3Config;


    @GetMapping
    public List<S3ObjectSummary> get() {
        ObjectListing objectListing = amazonS3.listObjects(awsS3Config.getBucketName());
        return objectListing.getObjectSummaries();
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile imagen) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imagen.getBytes());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(imagen.getSize());
        String fileUrl = awsS3Config.getS3EndpointUrl() + "/" + awsS3Config.getBucketName() + "/" + imagen.getOriginalFilename();
        amazonS3.putObject(
                awsS3Config.getBucketName(), imagen.getOriginalFilename(), byteArrayInputStream, objectMetadata);
        return fileUrl;
    }
}
