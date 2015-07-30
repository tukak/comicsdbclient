package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.model.Series;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class SeriesResultEvent extends AbstractResultEvent<Series> {
    public SeriesResultEvent(List<Series> result) {
        super(result);
    }
}