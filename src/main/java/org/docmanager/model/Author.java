package org.docmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.docmanager.dto.authors.AuthorDTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "authors")
    private Set<Document> documents;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public AuthorDTO toDTO() {
        return AuthorDTO.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .documents(documents.stream()
                        .map(Document::toDTO)
                        .collect(Collectors.toSet()))
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
