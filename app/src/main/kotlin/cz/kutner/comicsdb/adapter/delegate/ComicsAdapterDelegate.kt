package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.activity.AuthorDetailActivity
import cz.kutner.comicsdb.activity.ImageViewSliderActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.activity.SeriesDetailActivity
import cz.kutner.comicsdb.databinding.FragmentComicsDetailBinding
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.Series
import kotlinx.android.synthetic.main.fragment_comics_detail.view.*
import java.util.*


class ComicsAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater
    /* TODO databinding */
    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh = holder as ComicsViewHolder
        val comics = items[position] as Comics

        vh.bind(comics)

        val authors = SpannableStringBuilder()
        for (author in comics.authors) {
            val formerLength = authors.length
            authors.append(author.role)
            authors.append(" ")
            authors.append(author.name)
            authors.append("\n")
            if (author.id != null && author.name != null) {
                authors.setSpan(AuthorClickableSpan(author.id), formerLength + (author.role?.length ?: 0) + 1, formerLength + (author.role?.length ?: 0) + author.name.length + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        vh.authors.text = authors
        vh.authors.movementMethod = LinkMovementMethod.getInstance()

        val series: Series? = comics.series
        if (series != null) {
            val seriesString = SpannableStringBuilder()
            seriesString.append(series.name)
            seriesString.setSpan(SeriesClickableSpan(series.id), 0, series.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            vh.series.text = seriesString
            vh.series.movementMethod = LinkMovementMethod.getInstance()
        }

        val allImages: ArrayList<Image> = ArrayList()
        if (comics.cover != null) {
            allImages.add(comics.cover as Image)
        }
        allImages.addAll(comics.samples)

        vh.cover.setOnClickListener { showImage(allImages, 0) }
        vh.sample1.setOnClickListener { showImage(allImages, 1) }
        vh.sample2.setOnClickListener { showImage(allImages, 2) }
        vh.sample3.setOnClickListener { showImage(allImages, 3) }
        vh.sample4.setOnClickListener { showImage(allImages, 4) }
        vh.sample5.setOnClickListener { showImage(allImages, 5) }
        vh.sample6.setOnClickListener { showImage(allImages, 6) }
    }

    fun showImage(images: ArrayList<Image>, position: Int) {
        val intent = Intent(inflater.context, ImageViewSliderActivity::class.java)
        intent.putParcelableArrayListExtra(ImageViewSliderActivity.IMAGES, images)
        intent.putExtra(ImageViewSliderActivity.POSTITION, position)
        inflater.context.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val comicsBinding = FragmentComicsDetailBinding.inflate(inflater, parent, false)
        return ComicsViewHolder(comicsBinding)
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Comics
    }

    class AuthorClickableSpan(id: Int) : ClickableSpan() {
        private var id: Int = 0

        init {
            this.id = id
        }

        override fun onClick(widget: View?) {
            if (widget != null) {
                val intent = Intent(widget.context, AuthorDetailActivity::class.java)
                intent.putExtra(MainActivity.AUTHOR_ID, id)
                widget.context.startActivity(intent)
            }
        }
    }

    class SeriesClickableSpan(id: Int) : ClickableSpan() {
        private var id: Int = 0

        init {
            this.id = id
        }

        override fun onClick(widget: View?) {
            if (widget != null) {
                val intent = Intent(widget.context, SeriesDetailActivity::class.java)
                intent.putExtra(MainActivity.SERIES_ID, id)
                widget.context.startActivity(intent)
            }
        }
    }

    class ComicsViewHolder(val binding: FragmentComicsDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        internal var cover: ImageView = itemView.cover
        internal var series: TextView = itemView.series
        internal var authors: TextView = itemView.authors
        internal var sample1: ImageView = itemView.sample1
        internal var sample2: ImageView = itemView.sample2
        internal var sample3: ImageView = itemView.sample3
        internal var sample4: ImageView = itemView.sample4
        internal var sample5: ImageView = itemView.sample5
        internal var sample6: ImageView = itemView.sample6

        fun bind(comics: Comics) {
            binding.comics = comics
            binding.executePendingBindings()
        }

    }
}