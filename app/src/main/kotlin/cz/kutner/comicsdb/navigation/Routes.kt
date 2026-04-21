package cz.kutner.comicsdb.navigation

import kotlinx.serialization.Serializable

@Serializable
object MainRoute

@Serializable
data class ComicsDetailRoute(val id: Int)

@Serializable
data class SeriesDetailRoute(val id: Int)

@Serializable
data class AuthorDetailRoute(val id: Int)

@Serializable
data class SearchRoute(val query: String = "")

@Serializable
data class ImageViewerRoute(val position: Int = 0)
