package com.rtchat_api.real_time_chat_api.service;


import com.google.api.gax.paging.Page;
//import com.google.cloud.storage.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FirebaseStorageService {

    public void upload(MultipartFile imageFile, String imageName) throws IOException {
        InputStream inputStream = imageFile.getInputStream();
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create(imageName, inputStream, imageFile.getContentType());
    }

    public String download(String path) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(path);
        if (blob == null){
            return null;
        }
        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //blob.downloadTo(outputStream);
        URL signedUrl = blob.signUrl(20, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
        return signedUrl.toString();
    }

    public HashMap<Long, String> baixarAmigosFotoPerfil(List<Usuario> users){
        HashMap<Long, String> profile_pictures = new HashMap<>();

        for(Usuario user:users){
            String urlImage;

            urlImage = this.download("users/user_"+ user.getUsername()+"/profile_pic");

            if(urlImage != null) profile_pictures.put(user.getId(), urlImage);
            else profile_pictures.put(user.getId(), "default");
        }

//        profile_pictures = users.stream().map((usuario)-> {
//            try {
//                return this.download("users/user_"+ usuario.getUsername()+"/profile_pic");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        })

        return profile_pictures;
    }

}
