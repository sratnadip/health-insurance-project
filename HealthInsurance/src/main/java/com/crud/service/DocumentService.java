package com.crud.service;

import com.crud.entity.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    Document storeFile(MultipartFile file, Long userId, String documentName);
    List<Document> getAllDocuments();
    Document getDocumentById(Long id);
    Document updateDocument(Long id, MultipartFile file, String documentName);
    void deleteDocument(Long id);

    // download support
    Resource loadFileAsResource(Long id);
}
