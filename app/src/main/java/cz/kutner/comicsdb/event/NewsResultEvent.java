package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.NewsItem;

public class NewsResultEvent extends AbstractResultEvent<NewsItem> {
    public NewsResultEvent(List<NewsItem> result) {
        super(result);
    }
}