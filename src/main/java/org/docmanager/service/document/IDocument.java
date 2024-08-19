package org.docmanager.service.document;

import org.docmanager.dto.document.CreateDocumentDTO;
import org.docmanager.dto.document.DocumentDTO;
import org.docmanager.dto.document.UpdateDocumentDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;

public interface IDocument {

    DocumentDTO createDocument(CreateDocumentDTO documentDTO) throws AlreadyExistException;

    DocumentDTO getDocumentById(Long documentId) throws NotFoundException;

    DocumentDTO getDocumentByTitle(String title) throws NotFoundException;

    void deleteDocument(Long documentId) throws NotFoundException;

    DocumentDTO updateDocument(Long documentId, UpdateDocumentDTO documentDTO) throws NotFoundException;

}
