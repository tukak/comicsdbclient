package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Comics;

public class ComicsSearchResultEvent extends AbstractResultEvent<Comics> {
    public ComicsSearchResultEvent(List<Comics> result) {
        super(result);
    }
}