package cz.kutner.comicsdb.event;

import java.util.List;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.5.2015.
 */
public class AbstractResultEvent<T> {
    List<T> result;

    public AbstractResultEvent() {
    }

    public AbstractResultEvent(List<T> result) {
        this.result = result;
    }

    public List<T> getResult() {
        return result;
    }
}
