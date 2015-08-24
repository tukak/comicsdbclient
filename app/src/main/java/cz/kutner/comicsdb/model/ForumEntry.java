package cz.kutner.comicsdb.model;

public class ForumEntry {
    private String nick;
    private String text;
    private String iconUrl;
    private String time;
    private String forum;

    public ForumEntry(String nick, String time, String forum, String text) {
        this.time = time;
        this.forum = forum;
        this.nick = nick;
        this.text = text;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNick() {
        return nick;
    }

    public String getText() {
        return text;
    }

    public String getIconUrl() {
        return iconUrl;
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
}


