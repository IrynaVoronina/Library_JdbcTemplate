package com.example.jdbctemplate.model;

import lombok.Data;
import java.util.Map;

@Data
public class Book {
    private final int id;
    private final String name;
    private final String keyword;
    private final Map<Integer, Author> authors;
}

