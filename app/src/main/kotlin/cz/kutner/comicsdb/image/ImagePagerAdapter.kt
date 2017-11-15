package cz.kutner.comicsdb.image

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import cz.kutner.comicsdb.model.Image
import java.util.*

class ImagePagerAdapter(fm: FragmentManager, private val images: ArrayList<Image>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ImageViewFragment.newInstance(images[position])
    }

    override fun getPageTitle(position: Int): CharSequence {
        return images[position].caption
    }

    override fun getCount(): Int {
        return images.count()
    }
}
