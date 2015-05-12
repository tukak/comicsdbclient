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

    //TODO nastavit čas do příspěvku
    //TODO načítat obrázky
    public Comment(String nick, Integer stars, String text, String time) {
        this.nick = nick;
        this.stars = stars;
        this.text = text;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Bitmap getIcon() {

        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
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
