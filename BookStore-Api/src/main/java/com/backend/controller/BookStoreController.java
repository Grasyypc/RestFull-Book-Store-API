package com.backend.controller;

import com.backend.Utility.Constants;
import com.backend.entity.Book;
import com.backend.model.BookModel;
import com.backend.model.ResponseMessage;
import com.backend.service.IBookStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.List;

@RestController
@RequestMapping("/book-api")
@Tag(name = "Adventures Of Ideas", description = " Our name reflects our belief: every great adventure begins with a single idea. Step inside, and let your next idea take you on a journey of learning, creativity, and transformation !! ")
public class BookStoreController {

    @Autowired
    private IBookStoreService bookStoreService;

    @PostMapping("/registerbook")
    @Operation(summary = " Book details recorded successfully — welcome  !! ", description = " A seamless process to register and manage Book information for a Good Collection and Customer experience !! ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Books Resgistered Sucessfully !! ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class))})})
    public ResponseEntity<ResponseMessage> createBook(@Valid @RequestBody BookModel bookModel) {
        Book registered = bookStoreService.registerBook(bookModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "Book is Registered with Author in Database !!", registered));
    }


    @GetMapping("/getallbook")
    @Operation(summary = "Successfully retrieved all Book data — explore the full list of book with ease !!",
            description = "Effortlessly access complete tourist data for better insights and management !!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Retrieved All book data", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class))
            })})
    public ResponseEntity<ResponseMessage> showAllBook(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                                       @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                       @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                       @RequestParam(required = false, defaultValue = "ASC") String sortDir) {
        try {
            Sort sort = null;
            if (sortDir.equalsIgnoreCase("ASC")) {
                sort = Sort.by(sortBy).ascending();
            } else {
                sort = Sort.by(sortBy).descending();
            }
            List<BookModel> shownAllBook = bookStoreService.showAllBook(PageRequest.of(pageNo - 1, pageSize, sort));
            if (!shownAllBook.isEmpty()) {
                return ResponseEntity.ok(new ResponseMessage(
                        HttpStatus.OK.value(), Constants.SUCCESS, "Successfully Retrieved all Books With Authors !!", shownAllBook));
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage(
                        HttpStatus.NO_CONTENT.value(), Constants.FAILED, "No books found for the given criteria"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.FAILED, "Error retrieving books"));
        }
    }

    @GetMapping("/findbook/{id}")
    @Operation(summary = "Successfully fetched the Book using their unique ID !! ", description = "Quickly access individual Book information by their unique identifier for precise data handling !! ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Books are successfully fetch by their unique id !! ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class))})})
    public ResponseEntity<ResponseMessage> retrieveBooksById(@PathVariable Long id) {
        try {
            if (id == null || id == 0) {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "book Id cannot be empty!"));
            }
            BookModel bookById = bookStoreService.findBookById(id);
            if (bookById != null) {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "Book Id successfuly retrieved ", bookById));
            } else {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Book Id not found failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Book Id getting failed"));
        }
    }

    @PutMapping("/updatebook")
    @Operation(summary = "Successfully Update the Book record associated with the given ID", description = " Update individual Book and Author Details using their unique ID !! ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book Updated By id !! ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class))})})
    public ResponseEntity<ResponseMessage> modifyBookById(@Valid @RequestBody BookModel bookModel) {
        String updateBookDetails = (bookStoreService.updateBookDetails(bookModel));
        return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, " Book record associated with the given ID is Successfully Update !!  ", updateBookDetails));
    }

    @DeleteMapping("/deletebook/{id}")
    @Operation(summary = "Successfully removed the Book record associated with the given ID", description = " delete individual Book Details using their unique ID !! ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book Deleted By id !! ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class))})})
    public ResponseEntity<ResponseMessage> removeBookAndAuthorById(@PathVariable Long id) {
        try {
            String bookById = bookStoreService.deleteBookById(id);
            if (bookById != null) {
                return ResponseEntity.ok(
                        new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS, "Successfully removed the Book record with the given Id !!", bookById));
            } else {
                return ResponseEntity.ok(
                        new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "An internal error occurred while deleting note with ID !! "));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED, " No book found in the database for the given ID !! " + id));
        }
    }
}

