package cz.kutner.comicsdb.connector.helper;

public class ForumHelper {
    public static int getForumId(String forumName) {
        int forumId = 0;
        switch (forumName) {
            case "* Připomínky a návrhy":
                forumId = 1;
                break;
            case "Fabula Rasa":
                forumId = 10;
                break;
            case "Filmový klub":
                forumId = 5;
                break;
            case "Pindárna":
                forumId = 3;
                break;
            case "Povinná četba":
                forumId = 4;
                break;
            case "Poznej comics nebo postavu":
                forumId = 9;
                break;
            case "Sběratelský klub":
                forumId = 6;
                break;
            case "Slevy, výprodeje, bazary":
                forumId = 11;
                break;
            case "Srazy, cony, festivaly":
                forumId = 8;
                break;
            case "Stripy, jouky, fejky :)":
                forumId = 8;
                break;
        }
        return forumId;
    }
}
