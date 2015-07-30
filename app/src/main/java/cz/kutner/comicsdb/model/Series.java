package cz.kutner.comicsdb.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 24.3.2015.
 */
public class Series {
    private String name;
    private Integer id;
    private String notes;
    private Integer numberOfComicses;
    private ArrayList<Comics> comicses;

    public Series(String name, Integer id, Integer numberOfComicses) {
        this.name = name;
        this.id = id;
        this.numberOfComicses = numberOfComicses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getNumberOfComicses() {
        return numberOfComicses;
    }

    public void setNumberOfComicses(Integer numberOfComicses) {
        this.numberOfComicses = numberOfComicses;
    }

    public ArrayList<Comics> getComicses() {
        return comicses;
    }

    public void setComicses(ArrayList<Comics> comicses) {
        this.comicses = comicses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Series series = (Series) o;

        if (name != null ? !name.equals(series.name) : series.name != null) return false;
        if (id != null ? !id.equals(series.id) : series.id != null) return false;
        return !(numberOfComicses != null ? !numberOfComicses.equals(series.numberOfComicses) : series.numberOfComicses != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (numberOfComicses != null ? numberOfComicses.hashCode() : 0);
        return result;
    }
}
