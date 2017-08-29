package cz.kutner.comicsdb.model

data class Comics(var name: String, var id: Int): Item {
    var published: String? = ""
    var rating: Double =  0.0

    fun getIntRating(): Int = Math.round(rating*20).toInt()

}
