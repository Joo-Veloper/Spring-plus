package io.joo.plus.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,10}")
    private String userId;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}")
    private String password;
}
