package cz.kutner.comicsdb.model

import java.util.ArrayList

data class Comics() {
    var name: String? = null
    var id: Int? = null
    var published: String? = null
    var voteCount: Int? = null
    var rating: Int? = null
    var genre: String? = null
    var publisher: String? = null
    var issn: String? = null
    var issueNumber: String? = null
    var binding: String? = null
    var format: String? = null
    var pagesCount: String? = null
    var print: String? = null
    var originalName: String? = null
    var originalPublisher: String? = null
    var price: String? = null
    var description: String? = null
    var notes: String? = null
    var authors: String? = null
    var series: String? = null
    var comments: ArrayList<Comment>? = null
        private set
    var coverUrl: String? = null

    init {
        this.comments = ArrayList<Comment>()
    }

    constructor(name: String, id: Int?) : this() {
        this.name = name
        this.id = id
        this.rating = 0
        this.comments = ArrayList<Comment>()
    }

    fun addComment(comment: Comment) {
        comments!!.add(comment)
    }
}
