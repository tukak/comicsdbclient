package cz.kutner.comicsdb.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import cz.kutner.comicsdb.databinding.FragmentImageViewBinding
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.utils.loadUrl

class ImageViewFragment : Fragment() {
    private var _binding: FragmentImageViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val image: Image? = arguments?.getParcelable(IMAGE)
        if (image != null) binding.imageView.loadUrl(image.fullUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IMAGE = "IMAGE"
        fun newInstance(image: Image?): ImageViewFragment {
            val args = bundleOf(IMAGE to image)
            val fragment = ImageViewFragment()
            fragment.arguments = args
            return fragment
        }
    }
}