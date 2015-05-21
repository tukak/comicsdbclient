package cz.kutner.comicsdb.event;

import java.util.List;

import cz.kutner.comicsdb.model.ForumEntry;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ForumResultEvent {
    private List<ForumEntry> result;

    public ForumResultEvent(List<ForumEntry> result) {
        this.result = result;
    }

    public List<ForumEntry> getResult() {
        return result;
    }
}