package cz.kutner.comicsdb.model;

import android.graphics.Bitmap;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class Classified {
    private String nick;
    private String text;
    private Bitmap icon;
    private String time;
    private String category;

    public Classified(String nick, String time, String category, String text) {
        this.time = time;
        this.category = category;
        this.nick = nick;
        this.text = text;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getNick() {
        return nick;
    }

    public String getText() {
        return text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public String getTime() {
        return time;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classified that = (Classified) o;

        if (!nick.equals(that.nick)) return false;
        if (!text.equals(that.text)) return false;
        if (!time.equals(that.time)) return false;
        return category.equals(that.category);

    }
}

