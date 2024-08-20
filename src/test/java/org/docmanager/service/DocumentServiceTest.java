package org.docmanager.service;

import org.docmanager.dto.document.CreateDocumentDTO;
import org.docmanager.dto.document.DocumentDTO;
import org.docmanager.dto.document.UpdateDocumentDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.model.Document;
import org.docmanager.repository.AuthorRepository;
import org.docmanager.repository.DocumentRepository;
import org.docmanager.service.document.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private DocumentService documentService;

    private Document document;
    private DocumentDTO documentDTO;
    private CreateDocumentDTO createDocumentDTO;
    private UpdateDocumentDTO updateDocumentDTO;

    @BeforeEach
    void setUp() {
        document = Document.builder()
                .id(1L)
                .title("Sample Title")
                .body("Sample Body")
                .authors(new HashSet<>())
                .references(new HashSet<>())
                .build();

        documentDTO = DocumentDTO.builder()
                .id(1L)
                .title("Sample Title")
                .body("Sample Body")
                .build();

        createDocumentDTO = new CreateDocumentDTO("Sample Title", "Sample Body", Collections.emptyList(), Collections.emptyList());
        updateDocumentDTO = new UpdateDocumentDTO("Updated Title", "Updated Body");
    }


    @Test
    void createDocument_ShouldThrowAlreadyExistException_WhenTitleAlreadyExists() {
        when(documentRepository.existsByTitle(createDocumentDTO.getTitle())).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> documentService.createDocument(createDocumentDTO));
        verify(documentRepository, times(1)).existsByTitle(createDocumentDTO.getTitle());
        verify(documentRepository, never()).save(any(Document.class));
    }


    @Test
    void getDocumentById_ShouldThrowNotFoundException_WhenDocumentDoesNotExist() {
        when(documentRepository.findById(document.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> documentService.getDocumentById(document.getId()));
        verify(documentRepository, times(1)).findById(document.getId());
    }

    @Test
    void deleteDocument_ShouldDeleteDocument_WhenDocumentExists() throws NotFoundException {
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));

        documentService.deleteDocument(document.getId());

        verify(documentRepository, times(1)).findById(document.getId());
        verify(documentRepository, times(1)).deleteById(document.getId());
    }

    @Test
    void deleteDocument_ShouldThrowNotFoundException_WhenDocumentDoesNotExist() {
        when(documentRepository.findById(document.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> documentService.deleteDocument(document.getId()));
        verify(documentRepository, times(1)).findById(document.getId());
        verify(documentRepository, never()).deleteById(document.getId());
    }

    @Test
    void updateDocument_ShouldThrowAlreadyExistException_WhenTitleAlreadyExists() {
        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(documentRepository.existsByTitle(updateDocumentDTO.getTitle())).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> documentService.updateDocument(document.getId(), updateDocumentDTO));
        verify(documentRepository, times(1)).findById(document.getId());
        verify(documentRepository, times(1)).existsByTitle(updateDocumentDTO.getTitle());
        verify(documentRepository, never()).save(any(Document.class));
    }

    @Test
    void updateDocument_ShouldThrowNotFoundException_WhenDocumentDoesNotExist() {
        when(documentRepository.findById(document.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> documentService.updateDocument(document.getId(), updateDocumentDTO));
        verify(documentRepository, times(1)).findById(document.getId());
        verify(documentRepository, never()).existsByTitle(updateDocumentDTO.getTitle());
        verify(documentRepository, never()).save(any(Document.class));
    }

}