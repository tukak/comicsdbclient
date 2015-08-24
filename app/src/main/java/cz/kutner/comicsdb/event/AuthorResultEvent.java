package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Author;

public class AuthorResultEvent extends AbstractResultEvent<Author> {
    public AuthorResultEvent(List<Author> result) {
        super(result);
    }
}