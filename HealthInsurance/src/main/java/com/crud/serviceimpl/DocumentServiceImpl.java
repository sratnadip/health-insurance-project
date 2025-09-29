package com.crud.serviceimpl;

import com.crud.entity.Document;
import com.crud.entity.User;
import com.crud.repository.DocumentRepository;
import com.crud.repository.UserRepository;
import com.crud.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    private final String uploadDir = System.getProperty("user.home") + "/uploads/";

    @Override
    public Document storeFile(MultipartFile file, Long userId, String documentName) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String fullPath = uploadDir + fileName;

            file.transferTo(new File(fullPath));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            Document document = new Document();
            document.setUser(user);
            document.setDocumentName(documentName);
            document.setOriginalFileName(file.getOriginalFilename());
            document.setUploadedAt(LocalDateTime.now());
            document.setFilePath(fullPath);

            return documentRepository.save(document);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + id));
    }

    @Override
    public Document updateDocument(Long id, MultipartFile file, String documentName) {
        try {
            Document existingDoc = getDocumentById(id);

            if (documentName != null && !documentName.isEmpty()) {
                existingDoc.setDocumentName(documentName);
            }

            if (file != null && !file.isEmpty()) {
                File oldFile = new File(existingDoc.getFilePath());
                if (oldFile.exists()) oldFile.delete();

                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String fullPath = uploadDir + fileName;
                file.transferTo(new File(fullPath));

                existingDoc.setFilePath(fullPath);
                existingDoc.setOriginalFileName(file.getOriginalFilename());
                existingDoc.setUploadedAt(LocalDateTime.now());
            }

            return documentRepository.save(existingDoc);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update file", e);
        }
    }

    @Override
    public void deleteDocument(Long id) {
        Document existingDoc = getDocumentById(id);

        File file = new File(existingDoc.getFilePath());
        if (file.exists()) file.delete();

        documentRepository.delete(existingDoc);
    }

    @Override
    public Resource loadFileAsResource(Long documentId) {
        Document doc = getDocumentById(documentId);

        try {
            Path filePath = Paths.get(doc.getFilePath()).toAbsolutePath().normalize();
            Path uploadBase = Paths.get(uploadDir).toAbsolutePath().normalize();

            if (!filePath.startsWith(uploadBase)) {
                throw new RuntimeException("Invalid file path");
            }

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or not readable: " + doc.getFilePath());
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found: " + doc.getFilePath(), ex);
        }
    }
}
