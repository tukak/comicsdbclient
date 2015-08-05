package cz.kutner.comicsdb.model;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 5.8.2015.
 */
public class Author {
    private String name;
    private String country;
    private Integer id;

    public Author(String name, String country, Integer id) {
        this.name = name;
        this.country = country;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (name != null ? !name.equals(author.name) : author.name != null) return false;
        if (country != null ? !country.equals(author.country) : author.country != null)
            return false;
        return !(id != null ? !id.equals(author.id) : author.id != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
