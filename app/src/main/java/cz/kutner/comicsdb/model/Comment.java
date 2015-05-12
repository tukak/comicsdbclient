package cz.kutner.comicsdb.model;

import android.graphics.Bitmap;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 9.4.2015.
 */
public class Comment {
    private String nick;
    private Integer stars;
    private String text;
    private Bitmap icon;
    private String time;

    public Comment(String nick, Integer stars, String text, String time) {
        this.nick = nick;
        this.stars = stars;
        this.text = text;
        this.time = time;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public Bitmap getIcon() {

        return icon;
    }

    public String getNick() {
        return nick;
    }

    public Integer getStars() {
        return stars;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
