package com.myblog.payLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 5, message = "post title at least have 5 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "post description at least have 10 characters")
    private String description;
    @NotEmpty
    private String content;
}
