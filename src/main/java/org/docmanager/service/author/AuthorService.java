package org.docmanager.service.author;

import lombok.RequiredArgsConstructor;
import org.docmanager.dto.authors.AuthorDTO;
import org.docmanager.dto.authors.CreateAuthorDTO;
import org.docmanager.dto.authors.UpdateAuthorDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.model.Author;
import org.docmanager.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthor {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDTO createAuthor(CreateAuthorDTO authorDTO) throws AlreadyExistException {

        if (Boolean.TRUE.equals(this.authorRepository.existsByEmail(authorDTO.getEmail()))) {
            throw new AlreadyExistException(String.format("Author with email %s already exists", authorDTO.getEmail()));
        }

        return this.authorRepository.save(
                Author.builder()
                        .firstName(authorDTO.getFirstName())
                        .lastName(authorDTO.getLastName())
                        .email(authorDTO.getEmail())
                        .build()
        ).toDTO();

    }

    @Override
    public AuthorDTO getAuthorById(Long authorId) throws NotFoundException {
        return this.authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(String.format("Author with Id %s not found", authorId)))
                .toDTO();
    }

    @Override
    public void deleteAuthor(Long authorId) throws NotFoundException {
        AuthorDTO authorDTO = this.getAuthorById(authorId);
        this.authorRepository.deleteById(authorDTO.getId());
    }

    @Override
    public AuthorDTO updateAuthor(Long authorId, UpdateAuthorDTO authorDTO) throws NotFoundException {
        return null;
    }
}
