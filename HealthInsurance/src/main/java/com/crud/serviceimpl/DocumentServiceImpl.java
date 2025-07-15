package com.crud.serviceimpl;
import com.crud.entity.Document;
import com.crud.entity.User;
import com.crud.repository.DocumentRepository;
import com.crud.repository.UserRepository;
import com.crud.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Document storeFile(MultipartFile file, Long claimId, Long userId, String documentName, String documentType, String documentStatusFromRequest) {
        try {
            // ✅ Use a permanent upload directory (e.g., /Users/yourname/uploads/)
            String uploadDir = System.getProperty("user.home") + "/uploads/";
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
            document.setClaimId(claimId);
            document.setUser(user);
            document.setDocumentName(documentName);
            document.setDocumentType(documentType);
            document.setDocumentStatus("Uploaded"); // Automatically set to 'Uploaded'
            document.setUploadedAt(LocalDateTime.now());
            document.setFilePath(fullPath);

            return documentRepository.save(document);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}