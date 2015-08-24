package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Classified;

public class ClassifiedResultEvent extends AbstractResultEvent<Classified> {
    public ClassifiedResultEvent(List<Classified> result) {
        super(result);
    }
}