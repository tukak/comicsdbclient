package cz.kutner.comicsdb.model

public data class Comment(public val nick: String, public val stars: Int?, public val text: String, public val time: String) {
    public var iconUrl: String? = null

}
