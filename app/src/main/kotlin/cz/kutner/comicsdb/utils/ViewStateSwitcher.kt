package cz.kutner.comicsdb.utils

import android.view.View

class ViewStateSwitcher private constructor(
    private val contentView: View?,
    private val progressView: View?,
    private val errorView: View?,
    private val emptyView: View?
) {
    private val allViews = listOfNotNull(contentView, progressView, errorView, emptyView)

    private fun showOnly(view: View?) {
        allViews.forEach { it.visibility = View.GONE }
        view?.visibility = View.VISIBLE
    }

    fun showContentView() = showOnly(contentView)
    fun showProgressView() = showOnly(progressView)
    fun showErrorView() = showOnly(errorView)
    fun showEmptyView() = showOnly(emptyView)

    class Builder(
        @Suppress("UNUSED_PARAMETER") context: android.content.Context
    ) {
        private var contentView: View? = null
        private var progressView: View? = null
        private var errorView: View? = null
        private var emptyView: View? = null

        fun addContentView(view: View) = apply { contentView = view }
        fun addProgressView(view: View) = apply { progressView = view }
        fun addErrorView(view: View) = apply { errorView = view }
        fun addEmptyView(view: View) = apply { emptyView = view }

        fun build() = ViewStateSwitcher(contentView, progressView, errorView, emptyView)
    }
}
