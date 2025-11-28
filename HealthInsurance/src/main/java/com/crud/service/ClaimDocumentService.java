package com.crud.service;

import com.crud.entity.ClaimDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClaimDocumentService {

    // Upload single document
    ClaimDocument uploadClaimDocument(Long claimId, MultipartFile file, String documentType) throws IOException;

    // Upload multiple documents
    List<ClaimDocument> uploadMultipleClaimDocuments(Long claimId, MultipartFile[] files, String[] documentTypes) throws IOException;

    // Fetch all documents for claim
    List<ClaimDocument> getClaimDocuments(Long claimId);

    // Fetch document by ID
    ClaimDocument getClaimDocumentById(Long claimDocumentId);

    // Delete document
    void deleteClaimDocumentById(Long claimDocumentId);
}
