package cz.kutner.comicsdb.forumList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.ForumEntry
import kotlinx.coroutines.experimental.Deferred

class ForumListViewModel(application: Application) :
    AbstractAndroidViewModel<ForumEntry>(application) {
    override fun getJob(): Deferred<List<ForumEntry>?> =
        retrofitModule.forumListService.filteredForumList(start * count, count, filterId)
}