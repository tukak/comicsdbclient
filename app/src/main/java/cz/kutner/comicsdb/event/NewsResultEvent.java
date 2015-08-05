package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.NewsItem;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class NewsResultEvent extends AbstractResultEvent<NewsItem> {
    public NewsResultEvent(List<NewsItem> result) {
        super(result);
    }
}