package org.docmanager.dto.document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDocumentDTO {

    @NotNull
    private String title;

    @NotNull
    private String body;

    @NotNull
    @NotEmpty
    private List<Long> authorIds;

    @NotNull
    private List<Long> referenceIds;
}
