package cz.kutner.comicsdb.model

import java.util.*

data class Author(val name: String?, val country: String?, val id: Int?): Item {
    var comicses: ArrayList<Comics> = ArrayList()
    var bio: String? = null
    var notes: String? = null
    var photoUrl: String? = null
    var role: String? = null
}
