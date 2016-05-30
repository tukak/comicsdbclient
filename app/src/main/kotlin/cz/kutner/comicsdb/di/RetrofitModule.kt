package cz.kutner.comicsdb.di

import cz.kutner.comicsdb.connector.converter.*
import cz.kutner.comicsdb.connector.service.*
import dagger.Module
import dagger.Provides
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule(val baseUrl: String): AnkoLogger {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(baseUrl)
    }

    @Provides
    @Singleton
    fun provideSeriesListService(builder: Retrofit.Builder): SeriesListService {
        return builder.addConverterFactory(SeriesListConverterFactory.create()).build().create(SeriesListService::class.java)
    }

    @Provides
    @Singleton
    fun provideSeriesDetailService(builder: Retrofit.Builder): SeriesDetailService {
        return builder.addConverterFactory(SeriesDetailConverterFactory.create()).build().create(SeriesDetailService::class.java)

    }

    @Provides
    @Singleton
    fun provideAuthorDetailService(builder: Retrofit.Builder): AuthorDetailService {
        return builder.addConverterFactory(AuthorDetailConverterFactory.create()).build().create(AuthorDetailService::class.java)

    }

    @Provides
    @Singleton
    fun provideAuthorListService(builder: Retrofit.Builder): AuthorListService {
        return builder.addConverterFactory(AuthorListConverterFactory.create()).build().create(AuthorListService::class.java)

    }

    @Provides
    @Singleton
    fun provideClassifiedService(builder: Retrofit.Builder): ClassifiedService {
        return builder.addConverterFactory(ClassifiedConverterFactory.create()).build().create(ClassifiedService::class.java)

    }

    @Provides
    @Singleton
    fun provideComicsDetailService(builder: Retrofit.Builder): ComicsDetailService {
        return builder.addConverterFactory(ComicsDetailConverterFactory.create()).build().create(ComicsDetailService::class.java)

    }

    @Provides
    @Singleton
    fun provideComicsListService(builder: Retrofit.Builder): ComicsListService {
        info("Dagger vrací")
        return builder.addConverterFactory(ComicsListConverterFactory.create()).build().create(ComicsListService::class.java)

    }

    @Provides
    @Singleton
    fun provideForumService(builder: Retrofit.Builder): ForumService {
        return builder.addConverterFactory(ForumConverterFactory.create()).build().create(ForumService::class.java)

    }

    @Provides
    @Singleton
    fun provideNewsService(builder: Retrofit.Builder): NewsService {
        return builder.addConverterFactory(NewsConverterFactory.create()).build().create(NewsService::class.java)

    }

}