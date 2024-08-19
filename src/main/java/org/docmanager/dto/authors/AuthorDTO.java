package org.docmanager.dto.authors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.docmanager.dto.document.DocumentDTO;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Set<DocumentDTO> documents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
