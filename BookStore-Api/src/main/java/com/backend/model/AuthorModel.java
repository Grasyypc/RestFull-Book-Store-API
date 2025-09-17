package com.backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthorModel {
    private Long aid;
    @NotEmpty(message = "Author name must be provided and cannot be empty")
    @Size(min = 2, max = 100, message = "The author name must be longer than two characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name must not contain special characters")
    private String name;

    @NotEmpty(message = "Author email must be provided and cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    // toString


    @Override
    public String toString() {
        return "AuthorModel{" +
                "aid=" + aid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
