package cz.kutner.comicsdb.model

data class Filter(val id: Int, val title: String) {
    override fun toString(): String {
        return this.title
    }
}