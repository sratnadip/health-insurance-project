package com.crud.service;

import com.crud.entity.Document;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Document storeFile(MultipartFile file, Long userId, String documentName);
}
