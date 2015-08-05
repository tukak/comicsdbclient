package cz.kutner.comicsdb.model;

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

    public Integer getNumberOfComicses() {
        return numberOfComicses;
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
