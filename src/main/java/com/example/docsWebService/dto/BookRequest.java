package com.example.docsWebService.dto;

import com.example.docsWebService.entities.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    private String name;
    private String description;
    private Long authorId;
}
