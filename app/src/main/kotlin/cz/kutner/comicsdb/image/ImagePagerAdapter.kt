package cz.kutner.comicsdb.image

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.kutner.comicsdb.model.Image

class ImagePagerAdapter(activity: FragmentActivity, private val images: ArrayList<Image>?) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment = ImageViewFragment.newInstance(images?.get(position))

    override fun getItemCount(): Int = images?.size ?: 0

    fun getPageTitle(position: Int): CharSequence = images?.get(position)?.caption.toString()
}
