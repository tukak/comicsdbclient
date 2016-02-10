package cz.kutner.comicsdb.model

data class Comment(val nick: String, val stars: Int?, val text: String, val time: String) {
    var iconUrl: String? = null

}
