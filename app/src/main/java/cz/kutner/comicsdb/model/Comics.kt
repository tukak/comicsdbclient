package cz.kutner.comicsdb.model

import java.util.*

data class Comics(var name: String, var id: Int) {
    var published: String? = null
    var voteCount: Int? = null
    var rating: Int = 0
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
    var comments: ArrayList<Comment> = ArrayList()
        private set
    var coverUrl: String? = null
}
