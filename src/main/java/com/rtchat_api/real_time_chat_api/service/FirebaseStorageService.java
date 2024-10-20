package com.rtchat_api.real_time_chat_api.service;


import com.google.api.gax.paging.Page;
//import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FirebaseStorageService {

    public void upload(MultipartFile imageFile, String imageName) throws IOException {
        InputStream inputStream = imageFile.getInputStream();
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create(imageName, inputStream, imageFile.getContentType());
    }

    public byte[] download(String path) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(path);
        if (blob == null){
            throw new IOException("File not found: " + path);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob.downloadTo(outputStream);
        return outputStream.toByteArray();
    }

}
