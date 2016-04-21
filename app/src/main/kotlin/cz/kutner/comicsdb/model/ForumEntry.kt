package cz.kutner.comicsdb.model

data class ForumEntry(val nick: String, val time: String, val forum: String, val text: String):Item {
    var iconUrl: String? = null
}


