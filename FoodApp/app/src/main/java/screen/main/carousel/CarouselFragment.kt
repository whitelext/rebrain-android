package screen.main.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.whitelext.foodapp.R
import kotlinx.android.synthetic.main.fragment_carousel.view.*
import utils.BaseFragment

private const val STRING_KEY = "string"

/**
 *  [Fragment] that shows image for ViewPager
 */
class CarouselFragment : BaseFragment() {
    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carousel, container, false).apply {
            arguments?.getString(STRING_KEY)?.let {
                Glide.with(context)
                    .load(it)
                    .into(this.carousel_imageView)
            }
        }
    }

    companion object {
        fun newInstance(bannerImageUri: String) =
            CarouselFragment().apply {
                arguments = Bundle().apply {
                    putString(STRING_KEY, bannerImageUri)
                }
            }

        const val TAG = "CarouselFragment"
    }

}
