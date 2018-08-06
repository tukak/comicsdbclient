package cz.kutner.comicsdb.image

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import cz.kutner.comicsdb.model.Image
import java.util.*

class ImagePagerAdapter(fm: FragmentManager, private val images: ArrayList<Image>) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = ImageViewFragment.newInstance(images[position])

    override fun getPageTitle(position: Int): CharSequence = images[position].caption

    override fun getCount(): Int = images.count()
}
