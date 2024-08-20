package org.docmanager.service.document;

import org.docmanager.dto.document.CreateDocumentDTO;
import org.docmanager.dto.document.DocumentDTO;
import org.docmanager.dto.document.UpdateDocumentDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDocument {

    /**
     * Create a new document
     *
     * @param documentDTO the document to create
     * @return the created document
     * @throws AlreadyExistException if the document already exists
     */
    DocumentDTO createDocument(CreateDocumentDTO documentDTO) throws AlreadyExistException, NotFoundException;

    /**
     * Get a document by its id
     *
     * @param documentId the document id
     * @return the document
     * @throws NotFoundException if the document does not exist
     */
    DocumentDTO getDocumentById(Long documentId) throws NotFoundException;

    /**
     * Get a document by its title
     *
     * @param title the document title
     * @return the document
     * @throws NotFoundException if the document does not exist
     */
    DocumentDTO getDocumentByTitle(String title) throws NotFoundException;

    /**
     * Delete a document by its id
     *
     * @param documentId the document id
     * @throws NotFoundException if the document does not exist
     */
    void deleteDocument(Long documentId) throws NotFoundException;

    /**
     * Update a document by its id
     *
     * @param documentId  the document id
     * @param documentDTO the document to update
     * @return the updated document
     * @throws NotFoundException if the document does not exist
     */
    DocumentDTO updateDocument(Long documentId, UpdateDocumentDTO documentDTO) throws NotFoundException, AlreadyExistException;

    /**
     * Get all documents
     *
     * @param pageable the pagination information
     * @return the documents
     */
    Page<DocumentDTO> getAllDocuments(Pageable pageable);
}
