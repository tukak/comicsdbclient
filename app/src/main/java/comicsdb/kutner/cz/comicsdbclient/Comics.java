package comicsdb.kutner.cz.comicsdbclient;

/**
 * Created by Lukas.Kutner on 24.3.2015.
 */
public class Comics {
    private String title;
    private String url;
    private String published;
    private Integer voteCount;
    private Integer rating;

    public Comics() {
        super();
    }

    public Comics(String title, String url, String published, Integer voteCount, Integer rating) {
        this.title = title;
        this.url = url;
        this.published = published;
        this.voteCount = voteCount;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return this.title + " - " + published + "\n" + rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
