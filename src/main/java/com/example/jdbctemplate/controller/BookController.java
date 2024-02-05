package com.example.jdbctemplate.controller;

import com.example.jdbctemplate.model.Book;
import com.example.jdbctemplate.service.BookOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "BookController", description = "Controller for book CRUD operations")
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookOperationService bookOperationService;

    public BookController(BookOperationService bookOperationService) {
        this.bookOperationService = bookOperationService;
    }

    @Operation(summary = "Get list of all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the books",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Books not found", content = @Content)
    })
    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        return bookOperationService.getAllBooks();
    }


    @Operation(summary = "Find book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    @GetMapping("/{id}")
    public Book readBook(@Parameter(description = "id of book to be searched") @PathVariable int id) {
        return bookOperationService.readBook(id);
    }

    @Operation(summary = "Create book in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book was created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Book wasn't created", content = @Content)
    })
    @PostMapping("/create")
    public void createBook(@Parameter(description = "name of book to be created")
                           @RequestParam("name") String bookName,
                           @Parameter(description = "keyword of book to be created")
                           @RequestParam("keyword") String keyword,
                           @Parameter(description = "authors of book to be created")
                           @RequestParam("authorIds") List<Integer> authorIds) {
        bookOperationService.createBook(bookName, keyword, authorIds);
    }

    @Operation(summary = "Update book in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Book wasn't updated", content = @Content)
    })
    @PutMapping("/{id}")
    public void updateBook(@Parameter(description = "id of book to be searched") @PathVariable int id,
                           @Parameter(description = "name of book to be created")
                           @RequestParam("name") String bookName,
                           @Parameter(description = "keyword of book to be created")
                           @RequestParam("keyword") String keyword,
                           @Parameter(description = "authors of book to be created")
                           @RequestParam("authorIds") List<Integer> authorIds) {
        bookOperationService.updateBook(id, bookName, keyword, authorIds);
    }

    @Operation(summary = "Delete book in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book was deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Book wasn't deleted", content = @Content)
    })
    @DeleteMapping("/{id}")
    void deleteBook(@Parameter(description = "id of book to be searched") @PathVariable int id) {
        bookOperationService.deleteBook(id);
    }

    @Operation(summary = "Search book by name in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Book wasn't found", content = @Content)
    })
    @GetMapping("/searchByName")
    public List<Book> searchBooksByName(@Parameter(description = "part of the name of book to be searched")
                                            @RequestParam("query") String query) {
        return bookOperationService.searchBooksByName(query);
    }

    @Operation(summary = "Search book by author in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book was found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Book wasn't found", content = @Content)
    })
    @GetMapping("/searchByAuthor")
    public List<Book> searchBooksByAuthor(@Parameter(description = "part of the name of author to be searched")
                                              @RequestParam("query") String query) {
        return bookOperationService.searchBooksByAuthor(query);
    }
}


