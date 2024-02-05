package com.example.jdbctemplate.model;

import lombok.Data;

@Data
public class Author {
    private final int id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String birthplace;
}

