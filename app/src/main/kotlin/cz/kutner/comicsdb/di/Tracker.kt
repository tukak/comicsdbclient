package cz.kutner.comicsdb.di

interface Tracker {
    fun logVisit(screenName: String, category: String? = null, action: String? = null)

    fun logEvent(category: String?, action: String?)
}
