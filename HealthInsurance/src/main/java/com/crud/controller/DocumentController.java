package com.crud.controller;

import com.crud.entity.Document;
import com.crud.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // ----------------- Upload Document -----------------
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("documentName") String documentName
    ) {
        try {
            Document saved = documentService.storeFile(file, userId, documentName);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload document: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------- Get All Documents -----------------
    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    // ----------------- Get Document By Id -----------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentById(@PathVariable Long id) {
        try {
            Document document = documentService.getDocumentById(id);
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
        }
    }

    // ----------------- View Document in Browser -----------------
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewDocument(@PathVariable Long id) {
        try {
            Document document = documentService.getDocumentById(id);
            Path filePath = Paths.get(document.getFilePath()).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Detect file type
            String contentType;
            try {
                contentType = Files.probeContentType(filePath);
            } catch (IOException e) {
                contentType = "application/octet-stream";
            }

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + filePath.getFileName().toString() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ----------------- Download Document -----------------
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        try {
            Document document = documentService.getDocumentById(id);
            Path filePath = Paths.get(document.getFilePath()).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType;
            try {
                contentType = Files.probeContentType(filePath);
            } catch (IOException e) {
                contentType = "application/octet-stream";
            }

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ----------------- Update Document -----------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "documentName", required = false) String documentName
    ) {
        try {
            Document updated = documentService.updateDocument(id, file, documentName);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update document: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------- Delete Document -----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok("Document deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete document: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
 