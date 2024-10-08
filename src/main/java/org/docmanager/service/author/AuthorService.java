package org.docmanager.service.author;

import lombok.RequiredArgsConstructor;
import org.docmanager.dto.authors.AuthorDTO;
import org.docmanager.dto.authors.CreateAuthorDTO;
import org.docmanager.dto.authors.UpdateAuthorDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.model.Author;
import org.docmanager.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    @Transactional
    public void deleteAuthor(Long authorId) throws NotFoundException {
        AuthorDTO authorDTO = this.getAuthorById(authorId);
        this.authorRepository.deleteById(authorDTO.getId());
    }

    @Override
    @Transactional
    public AuthorDTO updateAuthor(Long authorId, UpdateAuthorDTO authorDTO) throws NotFoundException, AlreadyExistException {

        Author author = this.authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(String.format("Author with Id %s not found", authorId)));

        if (Boolean.TRUE.equals(this.authorRepository.existsByEmail(authorDTO.getEmail()))) {
            throw new AlreadyExistException(String.format("Author with email %s already exists", authorDTO.getEmail()));
        }

        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setEmail(authorDTO.getEmail());

        return this.authorRepository.save(author).toDTO();
    }

    @Override
    public Page<AuthorDTO> getAllAuthors(Pageable pageable) {
        return this.authorRepository.findAll(pageable).map(Author::toDTO);
    }
}
