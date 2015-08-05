package cz.kutner.comicsdb.event;

import java.util.List;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.5.2015.
 */
public class AbstractResultEvent<T> {
    private List<T> result;

    AbstractResultEvent() {
    }

    AbstractResultEvent(List<T> result) {
        this.result = result;
    }

    public List<T> getResult() {
        return result;
    }
}
