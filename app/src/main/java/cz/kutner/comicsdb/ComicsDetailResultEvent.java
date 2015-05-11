package cz.kutner.comicsdb;

import java.util.List;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class ComicsDetailResultEvent {

    private Comics result;

    public ComicsDetailResultEvent(Comics result) {
        this.result = result;
    }

    public Comics getResult() {
        return result;
    }
}