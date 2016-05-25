package cz.kutner.comicsdb.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import cz.kutner.comicsdb.fragment.ImageViewFragment
import cz.kutner.comicsdb.model.Image
import java.util.*

class ImagePagerAdapter(fm: FragmentManager, val images: ArrayList<Image>, position: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment = ImageViewFragment()
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return images[position].caption

    }

    override fun getCount(): Int {
        return images.count()
    }
}
