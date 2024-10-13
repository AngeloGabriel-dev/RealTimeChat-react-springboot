package com.rtchat_api.real_time_chat_api.service;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.firebase.cloud.StorageClient;

import java.io.FileInputStream;
import java.io.InputStream;

public class FirebaseStorageService {
    public String uploadFile(byte[] fileBytes, String fileName, String contentType){
        String bucketName = StorageClient.getInstance().bucket().getName();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        StorageClient.getInstance().bucket().create(blobInfo.getName(), fileBytes, blobInfo.getContentType());
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }
}
