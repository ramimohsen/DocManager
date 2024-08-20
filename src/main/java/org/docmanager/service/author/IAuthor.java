package org.docmanager.service.author;

import org.docmanager.dto.authors.AuthorDTO;
import org.docmanager.dto.authors.CreateAuthorDTO;
import org.docmanager.dto.authors.UpdateAuthorDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuthor {

    /**
     * Create a new author
     *
     * @param authorDTO the author to create
     * @return the created author
     * @throws AlreadyExistException if the author already exists
     */
    AuthorDTO createAuthor(CreateAuthorDTO authorDTO) throws AlreadyExistException;

    /**
     * Get author by id
     *
     * @param authorId the author id
     * @return the author
     * @throws NotFoundException if the author does not exist
     */
    AuthorDTO getAuthorById(Long authorId) throws NotFoundException;

    /**
     * Delete author by id
     *
     * @param authorId the author id
     * @throws NotFoundException if the author does not exist
     */
    void deleteAuthor(Long authorId) throws NotFoundException;

    /**
     * Update author
     *
     * @param authorId  the author id
     * @param authorDTO the author to update
     * @return the updated author
     * @throws NotFoundException if the author does not exist
     */
    AuthorDTO updateAuthor(Long authorId, UpdateAuthorDTO authorDTO) throws NotFoundException;


    /**
     * Get all authors
     *
     * @param pageable the pagination information
     * @return the list of authors
     */
    Page<AuthorDTO> getAllAuthors(Pageable pageable);
}
