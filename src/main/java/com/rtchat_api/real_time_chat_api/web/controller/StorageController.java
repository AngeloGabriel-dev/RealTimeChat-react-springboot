package com.rtchat_api.real_time_chat_api.web.controller;

import com.rtchat_api.real_time_chat_api.jwt.JwtUserDetails;
import com.rtchat_api.real_time_chat_api.service.FirebaseStorageService;
import com.rtchat_api.real_time_chat_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/storage")
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class StorageController {
    @Autowired
    private final FirebaseStorageService storageService;
    private final UsuarioService usuarioService;

    @PostMapping("/updateProfilePicture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file,
                                             @AuthenticationPrincipal JwtUserDetails userDetails){
        try {
            String folder = "user_"+userDetails.getUsername();
            storageService.upload(file, "users/" + folder + "/profile_pic");
            return ResponseEntity.ok().body("Imagem de perfil atualizada");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao fazer upload da imagem");
        }
    }

    @GetMapping("/downloadProfilePicture")
    public ResponseEntity<?> downloadProfilePicture(@AuthenticationPrincipal JwtUserDetails userDetails){
        try{
            String path = "users/user_"+userDetails.getUsername()+"/profile_pic";
            byte[] image = storageService.download(path);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(image);
        }
        catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao baixar image");
        }
    }
}


