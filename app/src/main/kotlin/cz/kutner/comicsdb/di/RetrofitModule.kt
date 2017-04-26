package cz.kutner.comicsdb.di

import cz.kutner.comicsdb.connector.converter.*
import cz.kutner.comicsdb.connector.service.*
import retrofit2.Retrofit

class RetrofitModule(baseUrl: String) {
    val seriesListService: SeriesListService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(SeriesListConverterFactory.create()).build().create(SeriesListService::class.java) }
    val seriesDetailService: SeriesDetailService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(SeriesDetailConverterFactory.create()).build().create(SeriesDetailService::class.java) }
    val authorDetailService: AuthorDetailService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(AuthorDetailConverterFactory.create()).build().create(AuthorDetailService::class.java) }
    val authorListService: AuthorListService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(AuthorListConverterFactory.create()).build().create(AuthorListService::class.java) }
    val classifiedService: ClassifiedService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ClassifiedConverterFactory.create()).build().create(ClassifiedService::class.java) }
    val comicsDetailService: ComicsDetailService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ComicsDetailConverterFactory.create()).build().create(ComicsDetailService::class.java) }
    val comicsListService: ComicsListService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ComicsListConverterFactory.create()).build().create(ComicsListService::class.java) }
    val forumService: ForumService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ForumConverterFactory.create()).build().create(ForumService::class.java) }
    val newsService: NewsService by lazy { Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(NewsConverterFactory.create()).build().create(NewsService::class.java) }
}