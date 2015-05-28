package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Classified;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ClassifiedResultEvent extends AbstractResultEvent<Classified> {
    public ClassifiedResultEvent(List<Classified> result) {
        super(result);
    }
}