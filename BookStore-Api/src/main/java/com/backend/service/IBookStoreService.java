package com.backend.service;

import com.backend.entity.Book;
import com.backend.model.BookModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookStoreService {

    Book registerBook(BookModel bookModel);

    List<BookModel> showAllBook(Pageable pageable);

    BookModel findBookById(Long id);

    String updateBookDetails(BookModel bookModel);

    String deleteBookById(Long id);

}
