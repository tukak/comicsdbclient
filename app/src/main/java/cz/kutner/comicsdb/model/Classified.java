package cz.kutner.comicsdb.model;


public class Classified {
    private String nick;
    private String text;
    private String time;
    private String category;
    private String iconUrl;

    public Classified(String nick, String time, String category, String text) {
        this.time = time;
        this.category = category;
        this.nick = nick;
        this.text = text;
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

    public String getCategory() {
        return category;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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


