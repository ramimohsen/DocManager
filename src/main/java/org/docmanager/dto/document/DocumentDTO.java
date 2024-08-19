package org.docmanager.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.docmanager.dto.authors.AuthorDTO;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDTO {

    private Long id;

    private String title;

    private String body;

    private Set<AuthorDTO> authors;

    private Set<DocumentDTO> references;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
