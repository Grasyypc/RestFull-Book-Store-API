package com.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookModel {


    private Long bid;

    @NotEmpty(message = " Book name must be provided and cannot be empty !!")
    @Size(min = 2, max = 100, message = " The book name must be longer than Two character !! ")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only alphabets and spaces")
    private String name;

    @NotEmpty
    @Size(min = 7, max = 50, message = " The Genre of the Book must be Longer than Twelve Character !! ")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only alphabets and spaces")
    private String genre;

    @NotNull(message = " Published date must not be Null  !! ")
    private LocalDate publishedDate;

    @NotNull(message = "Price must not be null")
    @PositiveOrZero(message = "Price must be zero or positive")
    private Double price;

    @NotEmpty(message = " Author Email must be provided and cannot be Empty and Null !! ")
    private String authorEmail;

    @NotEmpty(message = " Book name must be provided and cannot be empty !!")
    @Size(min = 2, max = 100, message = " The book name must be longer than Two character !! ")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only alphabets and spaces")
    private String authorName;

    private AuthorModel authorInfo;

    @Override
    public String toString() {
        return "BookModel{" +
                "bid=" + bid +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", publishedDate=" + publishedDate +
                ", price=" + price +
                ", author=" + authorInfo +
                '}';
    }
}
