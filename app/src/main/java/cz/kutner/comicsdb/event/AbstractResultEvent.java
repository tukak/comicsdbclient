package cz.kutner.comicsdb.event;

import java.util.List;

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
