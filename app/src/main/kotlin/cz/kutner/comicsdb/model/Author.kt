package cz.kutner.comicsdb.model

data class Author(val name: String?, val country: String?, val id: Int?) : Item {
    var role: String? = null
}
