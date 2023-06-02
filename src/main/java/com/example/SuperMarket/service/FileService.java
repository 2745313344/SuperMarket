package com.example.SuperMarket.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Component
public interface FileService {
    HashMap<String, String> uploadingMusicFile(MultipartFile file);

    boolean removeMusicFileByUrl(String url);
}
