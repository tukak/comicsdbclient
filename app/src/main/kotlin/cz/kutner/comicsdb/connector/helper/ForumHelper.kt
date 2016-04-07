package cz.kutner.comicsdb.connector.helper

object ForumHelper {
    fun getForumId(forumName: String): Int {
        var forumId = 0
        when (forumName) {
            "* Připomínky a návrhy" -> forumId = 1
            "Fabula Rasa" -> forumId = 10
            "Filmový klub" -> forumId = 5
            "Pindárna" -> forumId = 3
            "Povinná četba" -> forumId = 4
            "Poznej comics nebo postavu" -> forumId = 9
            "Sběratelský klub" -> forumId = 6
            "Slevy, výprodeje, bazary" -> forumId = 11
            "Srazy, cony, festivaly" -> forumId = 8
            "Stripy, jouky, fejky :)" -> forumId = 7
        }
        return forumId
    }
}
