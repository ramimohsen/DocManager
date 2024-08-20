package org.docmanager.controller;

import org.docmanager.dto.authors.AuthorDTO;
import org.docmanager.dto.authors.CreateAuthorDTO;
import org.docmanager.dto.authors.UpdateAuthorDTO;
import org.docmanager.service.author.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();

        authorDTO = AuthorDTO.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
    }

    @Test
    void getAuthorById_ShouldReturnAuthorDTO_WhenAuthorExists() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(authorDTO);

        mockMvc.perform(get("/api/v1/author/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    void createAuthor_ShouldReturnAuthorDTO_WhenAuthorIsCreated() throws Exception {
        when(authorService.createAuthor(any(CreateAuthorDTO.class))).thenReturn(authorDTO);

        mockMvc.perform(post("/api/v1/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(authorService, times(1)).createAuthor(any(CreateAuthorDTO.class));
    }

    @Test
    void updateAuthor_ShouldReturnAuthorDTO_WhenAuthorIsUpdated() throws Exception {
        when(authorService.updateAuthor(anyLong(), any(UpdateAuthorDTO.class))).thenReturn(authorDTO);

        mockMvc.perform(put("/api/v1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(authorService, times(1)).updateAuthor(anyLong(), any(UpdateAuthorDTO.class));
    }

}