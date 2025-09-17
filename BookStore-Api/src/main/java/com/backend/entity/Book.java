package com.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "JPA_BOOK_API")
@Getter
@Setter
@AllArgsConstructor

public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Column(name = "Book_Name", length = 100)
    private String name;

    @Column(name = "Genre", length = 100)
    private String genre;

    @Column(name = "Published_Date")
    private LocalDate publishedDate;

    @Column(name = "Price")
    private Double price;

    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "aid")
    @JsonBackReference
    private Author authorInfo;

    // MetaData
    @CreationTimestamp
    @Column(name = "Created_Date", insertable = true, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "Update_Date", insertable = true, updatable = false)
    private LocalDateTime updatedDate;

    // toString
    @Override
    public String toString() {
        return "Book{" +
                "bid=" + bid +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", publishedDate=" + publishedDate +
                ", price=" + price +
                '}';
    }

    public Book() {
        System.out.println(" 0 Param Constructor ");
    }
}

