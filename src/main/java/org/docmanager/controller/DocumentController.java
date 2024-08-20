package org.docmanager.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.docmanager.dto.document.CreateDocumentDTO;
import org.docmanager.dto.document.DocumentDTO;
import org.docmanager.dto.document.UpdateDocumentDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.service.document.DocumentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Document Controller", description = "Document controller")
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/document")
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "Get document by Id")
    @GetMapping("{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public DocumentDTO getDocumentById(@PathVariable("documentId") Long documentId) throws NotFoundException {
        return this.documentService.getDocumentById(documentId);
    }

    @Operation(summary = "Get document by title")
    @GetMapping("/title/{title}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public DocumentDTO getDocumentByTitle(@PathVariable("title") String title) throws NotFoundException {
        return this.documentService.getDocumentByTitle(title);
    }

    @Operation(summary = "Delete document")
    @DeleteMapping("/{documentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDocument(@PathVariable("documentId") Long documentId) throws NotFoundException {
        this.documentService.deleteDocument(documentId);
    }

    @Operation(summary = "Update document")
    @PutMapping("/{documentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public DocumentDTO updateDocument(@PathVariable("documentId") Long documentId, @RequestBody UpdateDocumentDTO documentDTO) throws NotFoundException, AlreadyExistException {
        return this.documentService.updateDocument(documentId, documentDTO);
    }

    @Operation(summary = "Create document")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public DocumentDTO createDocument(@RequestBody CreateDocumentDTO documentDTO) throws AlreadyExistException, NotFoundException {
        return this.documentService.createDocument(documentDTO);
    }

    @Operation(summary = "Get all documents")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public Page<DocumentDTO> getAllDocuments(Pageable pageable) {
        return this.documentService.getAllDocuments(pageable);
    }

}
