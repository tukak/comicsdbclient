package cz.kutner.comicsdb.di

import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.activity.*
import cz.kutner.comicsdb.fragment.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, RetrofitModule::class))
interface ApplicationComponent {
    fun inject(application: ComicsDBApplication)
    fun inject(seriesFragment: SeriesFragment)
    fun inject(authorFragment: AuthorFragment)
    fun inject(classifiedFragment: ClassifiedFragment)
    fun inject(comicsListFragment: ComicsListFragment)
    fun inject(forumFragment: ForumFragment)
    fun inject(newsFragment: NewsFragment)
    fun inject(aboutFragment: AboutFragment)
    fun inject(searchActivity: SearchActivity)
    fun inject(authorDetailActivity: AuthorDetailActivity)
    fun inject(comicsDetailActivity: ComicsDetailActivity)
    fun inject(seriesDetailActivity: SeriesDetailActivity)
    fun inject(imageViewSliderActivity: ImageViewSliderActivity)
}