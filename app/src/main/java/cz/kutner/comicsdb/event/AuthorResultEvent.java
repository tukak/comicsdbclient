package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Author;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class AuthorResultEvent extends AbstractResultEvent<Author> {
    public AuthorResultEvent(List<Author> result) {
        super(result);
    }
}