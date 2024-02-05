package com.example.jdbctemplate.controller;

import com.example.jdbctemplate.model.Author;
import com.example.jdbctemplate.service.AuthorOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.security.KeyException;
import java.util.List;

@Tag(name = "AuthorController", description = "Controller for author CRUD operations")
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorOperationService authorOperationService;

    public AuthorController(AuthorOperationService authorOperationService) {
        this.authorOperationService = authorOperationService;
    }

    @Operation(summary = "Get list of all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the authors",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "404", description = "Authors not found", content = @Content)
    })
    @GetMapping("/getAllAuthors")
    public List<Author> getAllAuthors() {
        return authorOperationService.getAllAuthors();
    }

    @Operation(summary = "Find author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the author",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    @GetMapping("/{id}")
    public Author readAuthor(@Parameter(description = "id of author to be searched") @PathVariable int id) {

        try {
            var author = authorOperationService.read(id);
            return author;
        } catch (Exception ex) {
            //return new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", ex);
            return null;
        }
    }

    @Operation(summary = "Create author in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author was created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "404", description = "Author wasn't created", content = @Content)
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED) // глянути інші статуси
    public Author createAuthor(@Parameter(description = "author to be created") @RequestBody Author author) {
        return authorOperationService.create(author);
    }

    @Operation(summary = "Update author in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author was updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "404", description = "Author wasn't updated", content = @Content)
    })
    @PutMapping("/update")
    public Author updateAuthor(@Parameter(description = "author to be updated") @RequestBody Author author) {
        return authorOperationService.updateAuthor(author);
    }

    @Operation(summary = "Delete author in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author was deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "404", description = "Author wasn't deleted", content = @Content)
    })
    @DeleteMapping("/{id}")
    void deleteAuthor(@Parameter(description = "author to be deleted") @PathVariable int id) throws KeyException {
        authorOperationService.delete(authorOperationService.read(id));
    }
}


