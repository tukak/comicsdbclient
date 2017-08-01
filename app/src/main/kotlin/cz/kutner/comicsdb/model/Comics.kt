package cz.kutner.comicsdb.model

data class Comics(var name: String, var id: Int): Item {
    var published: String? = ""
    var rating: Int = 0
}
