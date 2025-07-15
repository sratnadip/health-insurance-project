package com.crud.service;

import com.crud.entity.Document;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Document storeFile(MultipartFile file, Long claimId, Long userId, String documentName, String documentType, String documentStatus);
}
