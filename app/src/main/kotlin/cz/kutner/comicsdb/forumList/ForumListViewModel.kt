package cz.kutner.comicsdb.forumList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.ForumEntry
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class ForumListViewModel(application: Application) :
    AbstractAndroidViewModel<ForumEntry>(application) {
    override fun getJob(): Deferred<List<ForumEntry>?> = async(CommonPool) {
        retrofitModule.forumListService.filteredForumList(start * count, count, filterId).execute()
            .body()
    }
}