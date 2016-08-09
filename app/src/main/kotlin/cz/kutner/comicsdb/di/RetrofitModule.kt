package cz.kutner.comicsdb.di

import cz.kutner.comicsdb.connector.converter.*
import cz.kutner.comicsdb.connector.service.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule(val baseUrl: String) {
    
    @Provides
    @Singleton
    fun provideSeriesListService(): SeriesListService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(SeriesListConverterFactory.create()).build().create(SeriesListService::class.java)
    }

    @Provides
    @Singleton
    fun provideSeriesDetailService(): SeriesDetailService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(SeriesDetailConverterFactory.create()).build().create(SeriesDetailService::class.java)

    }

    @Provides
    @Singleton
    fun provideAuthorDetailService(): AuthorDetailService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(AuthorDetailConverterFactory.create()).build().create(AuthorDetailService::class.java)

    }

    @Provides
    @Singleton
    fun provideAuthorListService(): AuthorListService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(AuthorListConverterFactory.create()).build().create(AuthorListService::class.java)

    }

    @Provides
    @Singleton
    fun provideClassifiedService(): ClassifiedService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ClassifiedConverterFactory.create()).build().create(ClassifiedService::class.java)

    }

    @Provides
    @Singleton
    fun provideComicsDetailService(): ComicsDetailService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ComicsDetailConverterFactory.create()).build().create(ComicsDetailService::class.java)

    }

    @Provides
    @Singleton
    fun provideComicsListService(): ComicsListService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ComicsListConverterFactory.create()).build().create(ComicsListService::class.java)

    }

    @Provides
    @Singleton
    fun provideForumService(): ForumService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ForumConverterFactory.create()).build().create(ForumService::class.java)

    }

    @Provides
    @Singleton
    fun provideNewsService(): NewsService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(NewsConverterFactory.create()).build().create(NewsService::class.java)

    }

}