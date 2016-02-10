package cz.kutner.comicsdb.di

import cz.kutner.comicsdb.connector.converter.*
import cz.kutner.comicsdb.connector.service.*
import dagger.Module
import dagger.Provides
import retrofit.RestAdapter
import javax.inject.Singleton

@Module
public class RetrofitModule(val baseUrl: String) {

    @Provides
    @Singleton
    fun provideRestAdapterBuilder(): RestAdapter.Builder {
        return RestAdapter.Builder().setEndpoint(baseUrl)
    }

    @Provides
    @Singleton
    fun provideSeriesService(builder: RestAdapter.Builder): SeriesService {
        return builder.setConverter(SeriesConverter()).build().create(SeriesService::class.java)
    }

    @Provides
    @Singleton
    fun provideSeriesDetailService(builder: RestAdapter.Builder): SeriesDetailService {
        return builder.setConverter(SeriesDetailConverter()).build().create(SeriesDetailService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthorDetailService(builder: RestAdapter.Builder): AuthorDetailService {
        return builder.setConverter(AuthorDetailConverter()).build().create(AuthorDetailService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthorService(builder: RestAdapter.Builder): AuthorService {
        return builder.setConverter(AuthorConverter()).build().create(AuthorService::class.java)
    }

    @Provides
    @Singleton
    fun provideClassifiedService(builder: RestAdapter.Builder): ClassifiedService {
        return builder.setConverter(ClassifiedConverter()).build().create(ClassifiedService::class.java)
    }

    @Provides
    @Singleton
    fun provideComicsDetailService(builder: RestAdapter.Builder): ComicsService {
        return builder.setConverter(ComicsConverter()).build().create(ComicsService::class.java)
    }

    @Provides
    @Singleton
    fun provideComicsListService(builder: RestAdapter.Builder): ComicsListService {
        return builder.setConverter(ComicsListConverter()).build().create(ComicsListService::class.java)
    }

    @Provides
    @Singleton
    fun provideForumService(builder: RestAdapter.Builder): ForumService {
        return builder.setConverter(ForumConverter()).build().create(ForumService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsService(builder: RestAdapter.Builder): NewsService {
        return builder.setConverter(NewsConverter()).build().create(NewsService::class.java)
    }

}