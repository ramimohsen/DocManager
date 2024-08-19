package org.docmanager.service.author;

import org.docmanager.dto.authors.AuthorDTO;
import org.docmanager.dto.authors.CreateAuthorDTO;
import org.docmanager.dto.authors.UpdateAuthorDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;

public interface IAuthor {

    AuthorDTO createAuthor(CreateAuthorDTO authorDTO) throws AlreadyExistException;

    AuthorDTO getAuthorById(Long authorId) throws NotFoundException;

    void deleteAuthor(Long authorId) throws NotFoundException;

    AuthorDTO updateAuthor(Long authorId, UpdateAuthorDTO authorDTO) throws NotFoundException;
}
