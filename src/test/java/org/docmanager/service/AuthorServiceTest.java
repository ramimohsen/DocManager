package org.docmanager.service;

import org.docmanager.dto.authors.AuthorDTO;
import org.docmanager.dto.authors.CreateAuthorDTO;
import org.docmanager.dto.authors.UpdateAuthorDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.model.Author;
import org.docmanager.repository.AuthorRepository;
import org.docmanager.service.author.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;
    private AuthorDTO authorDTO;
    private CreateAuthorDTO createAuthorDTO;
    private UpdateAuthorDTO updateAuthorDTO;

    @BeforeEach
    void setUp() {
        author = Author.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        authorDTO = AuthorDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        createAuthorDTO = new CreateAuthorDTO("John", "Doe", "john.doe@example.com");
        updateAuthorDTO = new UpdateAuthorDTO("John", "Doe", "john.doe@example.com");
    }

    @Test
    void createAuthor_ShouldReturnAuthorDTO_WhenAuthorIsCreated() throws AlreadyExistException {
        when(authorRepository.existsByEmail(createAuthorDTO.getEmail())).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO result = authorService.createAuthor(createAuthorDTO);

        assertNotNull(result);
        assertEquals(authorDTO, result);
        verify(authorRepository, times(1)).existsByEmail(createAuthorDTO.getEmail());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void createAuthor_ShouldThrowAlreadyExistException_WhenEmailAlreadyExists() {
        when(authorRepository.existsByEmail(createAuthorDTO.getEmail())).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> authorService.createAuthor(createAuthorDTO));
        verify(authorRepository, times(1)).existsByEmail(createAuthorDTO.getEmail());
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void getAuthorById_ShouldReturnAuthorDTO_WhenAuthorExists() throws NotFoundException {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        AuthorDTO result = authorService.getAuthorById(author.getId());

        assertNotNull(result);
        assertEquals(authorDTO, result);
        verify(authorRepository, times(1)).findById(author.getId());
    }

    @Test
    void getAuthorById_ShouldThrowNotFoundException_WhenAuthorDoesNotExist() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.getAuthorById(author.getId()));
        verify(authorRepository, times(1)).findById(author.getId());
    }

    @Test
    void deleteAuthor_ShouldDeleteAuthor_WhenAuthorExists() throws NotFoundException {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        authorService.deleteAuthor(author.getId());

        verify(authorRepository, times(1)).findById(author.getId());
        verify(authorRepository, times(1)).deleteById(author.getId());
    }

    @Test
    void deleteAuthor_ShouldThrowNotFoundException_WhenAuthorDoesNotExist() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.deleteAuthor(author.getId()));
        verify(authorRepository, times(1)).findById(author.getId());
        verify(authorRepository, never()).deleteById(author.getId());
    }

    @Test
    void updateAuthor_ShouldReturnAuthorDTO_WhenAuthorIsUpdated() throws NotFoundException, AlreadyExistException {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(authorRepository.existsByEmail(updateAuthorDTO.getEmail())).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO result = authorService.updateAuthor(author.getId(), updateAuthorDTO);

        assertNotNull(result);
        assertEquals(authorDTO, result);
        verify(authorRepository, times(1)).findById(author.getId());
        verify(authorRepository, times(1)).existsByEmail(updateAuthorDTO.getEmail());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void updateAuthor_ShouldThrowAlreadyExistException_WhenEmailAlreadyExists() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(authorRepository.existsByEmail(updateAuthorDTO.getEmail())).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> authorService.updateAuthor(author.getId(), updateAuthorDTO));
        verify(authorRepository, times(1)).findById(author.getId());
        verify(authorRepository, times(1)).existsByEmail(updateAuthorDTO.getEmail());
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void updateAuthor_ShouldThrowNotFoundException_WhenAuthorDoesNotExist() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.updateAuthor(author.getId(), updateAuthorDTO));
        verify(authorRepository, times(1)).findById(author.getId());
        verify(authorRepository, never()).existsByEmail(updateAuthorDTO.getEmail());
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void getAllAuthors_ShouldReturnPageOfAuthorDTOs() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> authorPage = new PageImpl<>(Collections.singletonList(author));
        when(authorRepository.findAll(pageable)).thenReturn(authorPage);

        Page<AuthorDTO> result = authorService.getAllAuthors(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(authorDTO, result.getContent().getFirst());
        verify(authorRepository, times(1)).findAll(pageable);
    }
}