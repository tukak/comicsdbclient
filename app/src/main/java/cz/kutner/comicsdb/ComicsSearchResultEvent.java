package cz.kutner.comicsdb;

import java.util.List;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class ComicsSearchResultEvent {

    private List<Comics> result;

    public ComicsSearchResultEvent(List<Comics> result) {
        this.result = result;
    }

    public List<Comics> getResult() {
        return result;
    }
}
