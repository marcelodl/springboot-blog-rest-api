package com.springboot.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100, message = "Post title must have at least 2 characters and maximum of 100 characters")
    private String title;

    @NotEmpty
    @Size(min = 5, max = 500, message = "Post description must have at least 5 characters and maximum of 500 characters")
    private String description;

    @NotEmpty
    @Size(min = 10, max = 1500, message = "Post content must have at least 10 characters and maximum of 1500 characters")
    private String content;

    private Set<CommentDTO> comments;

    private Long categoryId;
}
