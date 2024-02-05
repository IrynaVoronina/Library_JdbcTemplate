package com.example.jdbctemplate.dao;

import com.example.jdbctemplate.model.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AuthorsDaoImpl implements AuthorDao {
    private final static String sqlSelectAllAuthors = "SELECT * FROM Author";
    private static final String sqlRead =
            "SELECT author_id, firstName, middleName, lastName, birthplace FROM Author WHERE author_id = ?";
    private static final String sqlCreate =
            "INSERT INTO Author (firstName, middleName, lastName, birthplace) VALUES (?, ?, ?, ?)";
    private static final String sqlUpdate =
            "UPDATE Author SET firstName = ?, middleName = ?, lastName = ?, birthplace = ? WHERE author_id = ?";
    private static final String sqlDelete = "DELETE FROM Author WHERE author_id = ?";
    private final static String sqlDeleteBookAuthors = "DELETE FROM book_author WHERE author_id = ?";


    JdbcTemplate jdbcTemplate;

    public AuthorsDaoImpl(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Author> getAllAuthors() {
        return jdbcTemplate.query(sqlSelectAllAuthors, AuthorsDaoImpl::mapRow);
    }

    @Override
    public Author read(int id) {
        return jdbcTemplate.queryForObject(sqlRead, AuthorsDaoImpl::mapRow, id);
    }

    protected static Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author(rs.getInt("author_id"),
                rs.getString("firstName"),
                rs.getString("middleName"),
                rs.getString("lastName"),
                rs.getString("birthPlace"));
    }

    @Override
    public Author create(Author author) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sqlCreate, new String[]{("author_id")});
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getMiddleName());
            preparedStatement.setString(3, author.getLastName());
            preparedStatement.setString(4, author.getBirthplace());

            return preparedStatement;
        }, keyHolder);
        return new Author(keyHolder.getKey().intValue(),
                author.getFirstName(),
                author.getMiddleName(),
                author.getLastName(),
                author.getBirthplace());
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update(sqlUpdate,
                author.getFirstName(),
                author.getMiddleName(),
                author.getLastName(),
                author.getBirthplace(),
                author.getId());
        return author;
    }


    @Override
    public void delete(Author author) {
        jdbcTemplate.update(sqlDeleteBookAuthors, author.getId());
        jdbcTemplate.update(sqlDelete, author.getId());
    }
}
