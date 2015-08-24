package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Series;

public class SeriesResultEvent extends AbstractResultEvent<Series> {
    public SeriesResultEvent(List<Series> result) {
        super(result);
    }
}