package ru.mf.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 250)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 250)
    private String lastName;
    @Email
    @NotBlank
    @Size(min = 6, max = 254)
    private String email;
}
