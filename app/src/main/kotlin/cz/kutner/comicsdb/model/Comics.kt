package cz.kutner.comicsdb.model

data class Comics(var name: String, var id: Int) : Item {
    var published: String? = ""
    var rating: Float = 0.0f
}
