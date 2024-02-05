package com.example.jdbctemplate.service;

import com.example.jdbctemplate.dao.AuthorDao;
import com.example.jdbctemplate.model.Author;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyException;
import java.util.List;

@Service
public class AuthorOperationServiceImpl implements AuthorOperationService{
    private final AuthorDao authorDao;

    public AuthorOperationServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> getAllAuthors(){
        return authorDao.getAllAuthors();
    }
    @Override
    public Author create(Author author) {
        return authorDao.create(author);
    }
    @Override
    public Author read(int id) throws KeyException {
        var author =  authorDao.read(id);

        if(author == null)
            throw new KeyException("author with ID "+ id +" does not exist in the database!");

        return author;
    }
    @Override
    public Author updateAuthor(Author author){
        return authorDao.updateAuthor(author);
    }

    @Override
    @Transactional
    public void delete(Author author){
        authorDao.delete(author);
    }
}
