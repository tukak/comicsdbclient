package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Comics;

public class ComicsDetailResultEvent extends AbstractResultEvent<Comics> {
    public ComicsDetailResultEvent(List<Comics> result) {
        super(result);
    }
}