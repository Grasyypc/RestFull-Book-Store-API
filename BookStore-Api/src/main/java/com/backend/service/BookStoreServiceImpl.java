package com.backend.service;

import com.backend.entity.Author;
import com.backend.entity.Book;
import com.backend.exception.BookNotFoundException;
import com.backend.exception.BooksAndAuthorNotFound;
import com.backend.model.AuthorModel;
import com.backend.model.BookModel;
import com.backend.repository.AuthorRepository;
import com.backend.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("bookservice")
public class BookStoreServiceImpl implements IBookStoreService {

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private BookRepository bookRepo;

    @Override
    public Book registerBook(BookModel bookModel) {
        System.out.println("BookModel:: " + bookModel + " " + bookModel.getAuthorInfo());
        Book newBook = new Book();
        BeanUtils.copyProperties(bookModel, newBook);
        System.out.println("New Book in Service" + newBook);
        Optional<Author> authorOptional = authorRepo.findByEmail(bookModel.getAuthorEmail());
        Author author = null;
        if (authorOptional.isPresent()) {
            author = authorOptional.get();
            newBook.setAuthorInfo(author);
            author.addBook(newBook);
        } else {
            author = new Author();
            author.setName(bookModel.getAuthorName());
            author.setEmail(bookModel.getAuthorEmail());
        }
        Author updatedAuthor = authorRepo.save(author);
        newBook.setAuthorInfo(updatedAuthor);
        Book book = bookRepo.save(newBook);
        return book;
    }

    //********************************************************************************************************************************
    @Override
    public List<BookModel> showAllBook(Pageable pageable) {

        List<Book> listBook = bookRepo.findAll(pageable).getContent();
        // List of Book Entity to List of Book Model
        List<BookModel> showBook = new ArrayList<>();
        listBook.forEach(book -> {
            Author showAuthorBook = book.getAuthorInfo();

            BookModel showBookModel = new BookModel();
            BeanUtils.copyProperties(book, showBookModel);

            AuthorModel author = new AuthorModel();
            BeanUtils.copyProperties(book.getAuthorInfo(), author);

            showBookModel.setAuthorInfo(author);
            showBook.add(showBookModel);
        });
        return showBook;
    }
    //********************************************************************************************************************************

    @Override
    public BookModel findBookById(Long id) {
        Book findBook = bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Not Found"));

        Author findAuthor = findBook.getAuthorInfo();
        BookModel bookModel = new BookModel();
        AuthorModel authorModel = new AuthorModel();
        // Copy properties
        BeanUtils.copyProperties(findBook, bookModel);
        BeanUtils.copyProperties(findAuthor, authorModel);
        // Set author info
        bookModel.setAuthorInfo(authorModel);
        return bookModel;
    }
    //********************************************************************************************************************************

    @Override
    public String updateBookDetails(BookModel bookModel) {
        // Fetch existing book
        Book updateBook = bookRepo.findById(bookModel.getBid())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Book Id"));

        // Copy fields (excluding ID)
        BeanUtils.copyProperties(bookModel, updateBook, "bid", "author");
        // Update Author also (if author info is included in BookModel)
        if (bookModel.getAuthorInfo() != null) {
            Author updateAuthor = authorRepo.findById(bookModel.getAuthorInfo().getAid())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Author Id"));
            BeanUtils.copyProperties(bookModel.getAuthorInfo(), updateAuthor, "aid");
            updateBook.setAuthorInfo(updateAuthor);
        }
        // Save both (due to cascade, only saving book may be enough)
        bookRepo.save(updateBook);
        return "Book and Author updated successfully with ID " + updateBook.getBid();
    }

    //********************************************************************************************************************************
    @Override
    public String deleteBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new BooksAndAuthorNotFound("No matching Book found for ID: " + id));
        // deletes book + author
        bookRepo.delete(book);
        return "Book with ID " + id + " and its Author deleted successfully!";
    }

}
