package cz.kutner.comicsdb.model;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 5.8.2015.
 */
public class NewsItem {
    private String title;
    private String nick;
    private String text;
    private String time;

    public NewsItem(String title, String nick, String text, String time) {
        this.title = title;
        this.nick = nick;
        this.text = text;
        this.time = time;
    }


    public String getTitle() {
        return title;
    }

    public String getNick() {
        return nick;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsItem newsItem = (NewsItem) o;

        if (title != null ? !title.equals(newsItem.title) : newsItem.title != null) return false;
        if (nick != null ? !nick.equals(newsItem.nick) : newsItem.nick != null) return false;
        if (text != null ? !text.equals(newsItem.text) : newsItem.text != null) return false;
        return !(time != null ? !time.equals(newsItem.time) : newsItem.time != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
