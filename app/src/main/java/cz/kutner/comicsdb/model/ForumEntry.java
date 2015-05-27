package cz.kutner.comicsdb.model;

import android.graphics.Bitmap;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ForumEntry {
    private String nick;
    private String text;
    private Bitmap icon;
    private String time;
    private String forum;

    public ForumEntry(String nick, String time, String forum, String text) {
        this.time = time;
        this.forum = forum;
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

    public String getForum() {
        return forum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForumEntry that = (ForumEntry) o;

        if (!nick.equals(that.nick)) return false;
        if (!text.equals(that.text)) return false;
        if (!time.equals(that.time)) return false;
        return forum.equals(that.forum);

    }

    @Override
    public int hashCode() {
        int result = nick.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + forum.hashCode();
        return result;
    }
}


