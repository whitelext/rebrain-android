package screen.main.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.whitelext.foodapp.R
import kotlinx.android.synthetic.main.fragment_carousel.view.*
import utils.BaseFragment

private const val IMG_KEY = "num"

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
            arguments?.getInt(IMG_KEY)?.let { this.carousel_imageView.setImageResource(it) }
        }
    }

    companion object {
        fun newInstance(imgId: Int) =
            CarouselFragment().apply {
                arguments = Bundle().apply {
                    putInt(IMG_KEY, imgId)
                }
            }

        const val TAG = "CarouselFragment"
    }

}
