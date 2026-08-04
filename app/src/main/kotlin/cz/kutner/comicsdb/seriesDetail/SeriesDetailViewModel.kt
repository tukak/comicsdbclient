package cz.kutner.comicsdb.seriesDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.model.SeriesDetail

class SeriesDetailViewModel(private val service: SeriesDetailService) :
    AbstractViewModel<SeriesDetail>() {
    override suspend fun getJob(id: Int): SeriesDetail =
        service.seriesDetail(id)
}
