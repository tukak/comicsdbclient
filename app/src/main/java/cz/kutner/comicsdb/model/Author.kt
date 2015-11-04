package cz.kutner.comicsdb.model

import java.util.*

public data class Author(public val name: String?, public val country: String?, public val id: Int?) {
    public var comicses: ArrayList<Comics> = ArrayList()
    public var bio: String? = null
    public var notes: String? = null
    var photoUrl: String? = null
    var role: String? = null
}
