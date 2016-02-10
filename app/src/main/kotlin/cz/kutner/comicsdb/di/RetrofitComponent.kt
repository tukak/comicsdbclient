package cz.kutner.comicsdb.di

import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.fragment.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, RetrofitModule::class))
interface RetrofitComponent {
    fun inject(application: ComicsDBApplication)
    fun inject(seriesFragment: SeriesFragment)
    fun inject(authorFragment: AuthorFragment)
    fun inject(classifiedFragment: ClassifiedFragment)
    fun inject(comicsListFragment: ComicsListFragment)
    fun inject(forumFragment: ForumFragment)
    fun inject(newsFragment: NewsFragment)
    fun inject(seriesDetailFragment: SeriesDetailFragment)
    fun inject(comicsDetailFragment: ComicsDetailFragment)
    fun inject(authorDetailFragment: AuthorDetailFragment)
}