package cz.kutner.comicsdb;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 9.4.2015.
 */
class Comment {
    private String nick;
    private Integer stars;
    private String text;

    public Comment(String nick, Integer stars, String text) {
        this.nick = nick;
        this.stars = stars;
        this.text = text;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
