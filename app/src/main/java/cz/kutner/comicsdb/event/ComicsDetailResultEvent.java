package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.Comics;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */

public class ComicsDetailResultEvent extends AbstractResultEvent<Comics> {
    public ComicsDetailResultEvent(List<Comics> result) {
        super(result);
    }
}