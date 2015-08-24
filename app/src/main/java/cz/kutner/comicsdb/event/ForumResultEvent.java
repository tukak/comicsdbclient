package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.ForumEntry;

public class ForumResultEvent extends AbstractResultEvent<ForumEntry> {
    public ForumResultEvent(List<ForumEntry> result) {
        super(result);
    }
}