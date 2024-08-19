package org.docmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.docmanager.dto.authors.AuthorDTO;
import org.docmanager.dto.authors.CreateAuthorDTO;
import org.docmanager.dto.authors.UpdateAuthorDTO;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.service.author.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Author Controller", description = "Author controller")
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Get author by Id")
    @GetMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public AuthorDTO getAuthorById(@PathVariable("authorId") Long authorId) throws NotFoundException {
        return this.authorService.getAuthorById(authorId);
    }

    @Operation(summary = "Create author")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorDTO createAuthor(@RequestBody CreateAuthorDTO createAuthorDTO) throws AlreadyExistException {
        return this.authorService.createAuthor(createAuthorDTO);
    }

    @Operation(summary = "Delete author")
    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAuthor(@PathVariable("authorId") Long authorId) throws NotFoundException {
        this.authorService.deleteAuthor(authorId);
    }

    @Operation(summary = "Update author")
    @PutMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthorDTO updateAuthor(@PathVariable("authorId") Long authorId, @RequestBody UpdateAuthorDTO updateAuthorDTO) throws NotFoundException {
        return this.authorService.updateAuthor(authorId, updateAuthorDTO);
    }

}
