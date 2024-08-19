package org.docmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.docmanager.dto.document.CreateDocumentDTO;
import org.docmanager.dto.document.DocumentDTO;
import org.docmanager.dto.document.UpdateDocumentDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.model.Document;
import org.docmanager.repository.DocumentRepository;
import org.docmanager.service.IDocument;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DocumentService implements IDocument {

    private final DocumentRepository documentRepository;

    @Override
    public DocumentDTO createDocument(CreateDocumentDTO documentDTO) throws AlreadyExistException {

        if (Boolean.TRUE.equals(this.documentRepository.existsByTitle(documentDTO.getTitle()))) {
            throw new AlreadyExistException("Document already exists");
        }

        Document document = Document.builder()
                .title(documentDTO.getTitle())
                .body(documentDTO.getBody())
                .build();
        return this.documentRepository.save(document).toDTO();
    }

    @Override
    public DocumentDTO getDocumentById(Long documentId) throws NotFoundException {
        return this.documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException("Document not found")).toDTO();
    }

    @Override
    public DocumentDTO getDocumentByTitle(String title) throws NotFoundException {
        return this.documentRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException("Document not found")).toDTO();
    }

    @Override
    public void deleteDocument(Long documentId) throws NotFoundException {
        DocumentDTO documentDTO = this.getDocumentById(documentId);
        this.documentRepository.deleteById(documentDTO.getId());
    }

    @Override
    public DocumentDTO updateDocument(Long documentId, UpdateDocumentDTO documentDTO) throws NotFoundException {
        return null;
    }
}
