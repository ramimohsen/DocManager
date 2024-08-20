package org.docmanager.service.document;

import lombok.RequiredArgsConstructor;
import org.docmanager.dto.document.CreateDocumentDTO;
import org.docmanager.dto.document.DocumentDTO;
import org.docmanager.dto.document.UpdateDocumentDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.model.Author;
import org.docmanager.model.Document;
import org.docmanager.repository.AuthorRepository;
import org.docmanager.repository.DocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class DocumentService implements IDocument {

    private final DocumentRepository documentRepository;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public DocumentDTO createDocument(CreateDocumentDTO documentDTO) throws AlreadyExistException, NotFoundException {

        if (Boolean.TRUE.equals(this.documentRepository.existsByTitle(documentDTO.getTitle()))) {
            throw new AlreadyExistException("Document already exists");
        }

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(documentDTO.getAuthorIds()));

        List<Long> missingAuthorIds = getMissingAuthorIds(authors.stream()
                .map(Author::getId), documentDTO.getAuthorIds());

        if (!missingAuthorIds.isEmpty()) {
            throw new NotFoundException("Author(s) not found with id(s): " + missingAuthorIds);
        }

        Set<Document> references = new HashSet<>(documentRepository.findAllById(documentDTO.getReferenceIds()));

        List<Long> missingReferenceIds = getMissingReferenceIds(documentDTO, references);

        if (!missingReferenceIds.isEmpty()) {
            throw new NotFoundException("Reference(s) not found with id(s): " + missingReferenceIds);
        }

        Document document = Document.builder()
                .title(documentDTO.getTitle())
                .body(documentDTO.getBody())
                .authors(authors)
                .references(references)
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
    @Transactional
    public void deleteDocument(Long documentId) throws NotFoundException {
        DocumentDTO documentDTO = this.getDocumentById(documentId);
        this.documentRepository.deleteById(documentDTO.getId());
    }

    @Override
    @Transactional
    public DocumentDTO updateDocument(Long documentId, UpdateDocumentDTO documentDTO) throws NotFoundException {
        return null;
    }

    @Override
    public Page<DocumentDTO> getAllDocuments(Pageable pageable) {
        return this.documentRepository.findAll(pageable).map(Document::toDTO);
    }

    private static List<Long> getMissingAuthorIds(Stream<Long> authors, List<Long> documentDTO) {
        Set<Long> foundAuthorIds = authors
                .collect(Collectors.toSet());

        return documentDTO.stream()
                .filter(id -> !foundAuthorIds.contains(id))
                .toList();
    }

    private static List<Long> getMissingReferenceIds(CreateDocumentDTO documentDTO, Set<Document> references) {
        Set<Long> foundReferenceIds = references.stream()
                .map(Document::getId)
                .collect(Collectors.toSet());

        return documentDTO.getReferenceIds().stream()
                .filter(id -> !foundReferenceIds.contains(id))
                .toList();
    }
}
