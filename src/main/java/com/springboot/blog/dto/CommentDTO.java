package com.springboot.blog.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {

    private long id;

    @NotEmpty
    @Size(min = 2, max = 100, message = "Comment name must have at least 2 characters and maximum of 100 characters")
    private String name;

    @NotEmpty
    @Email(message = "E-mail not valid")
    private String email;

    @NotEmpty
    @Size(min = 5, max = 1500, message = "Comment body must have at least 5 characters and maximum of 1500 characters")
    private String body;
}
