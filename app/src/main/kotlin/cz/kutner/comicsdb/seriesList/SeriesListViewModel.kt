package cz.kutner.comicsdb.seriesList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.Series

class SeriesListViewModel(private val service: SeriesListService) :
    AbstractPagedViewModel<Series>() {
    override suspend fun getJob(): List<Series> =
        service.getSeriesList(start * count, count, searchText)
}
