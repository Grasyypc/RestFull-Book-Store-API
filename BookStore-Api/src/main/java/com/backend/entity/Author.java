package com.backend.entity;

// Parent Class

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "JPA_AUTHOR_API")
@Getter
@Setter
@AllArgsConstructor
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;

    @Column(name = "Author_Name", length = 100)
    private String name;

    @Column(name = "Email", length = 50, unique = true)
    private String email;

    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "authorInfo")
    @JsonManagedReference
    private List<Book> books;

    // MetaData
    @CreationTimestamp
    @Column(name = "Created_Date", insertable = true, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "Update_Time", insertable = true, updatable = false)
    private LocalDateTime updatedDate;

    public void addBook(Book book) {
        this.books.add(book);
    }

    @Override
    public String toString() {
        return "Author{" +
                "aid=" + aid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Author() {
        System.out.println(" 0 - Param Constructor : ");
    }
}

