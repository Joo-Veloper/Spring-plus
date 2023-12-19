package io.joo.plus.post.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    @Size(min = 1, max = 500)
    private String title;
    @Size(min = 1, max = 5000)
    private String content;
}
