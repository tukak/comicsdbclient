package cz.kutner.comicsdb.model;

import android.graphics.Bitmap;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 9.4.2015.
 */
public class Comment {
    private String nick;
    private Integer stars;
    private String text;
    private String time;
    private String iconUrl;

    public Comment(String nick, Integer stars, String text, String time) {
        this.nick = nick;
        this.stars = stars;
        this.text = text;
        this.time = time;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTime() {
        return time;
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

}
