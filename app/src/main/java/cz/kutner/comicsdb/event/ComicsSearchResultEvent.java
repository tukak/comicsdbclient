package cz.kutner.comicsdb.event;

import android.util.Log;

import java.util.List;

import cz.kutner.comicsdb.model.Comics;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class ComicsSearchResultEvent {

    private List<Comics> result;

    public ComicsSearchResultEvent(List<Comics> result) {
        this.result = result;
    }

    public List<Comics> getResult() {
        Log.i("CSR", "Vracíme výsledek");
        return result;
    }

}
