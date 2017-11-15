package cz.kutner.comicsdb.image

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.utils.loadUrl
import kotlinx.android.synthetic.main.fragment_image_view.*

class ImageViewFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val image: Image? = arguments?.getParcelable(IMAGE)
        if (image != null) imageView.loadUrl(image.fullUrl)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_view, null)
    }

    companion object {
        val IMAGE = "IMAGE"
        fun newInstance(image: Image): ImageViewFragment {
            val args = Bundle()
            args.putParcelable(IMAGE, image)
            val fragment = ImageViewFragment()
            fragment.arguments = args
            return fragment
        }
    }
}