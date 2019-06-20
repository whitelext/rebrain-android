package screen.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodapp.R
import kotlinx.android.synthetic.main.fragment_main.*
import screen.main.carousel.adapter.CarouselStatePageAdapter
import utils.BaseFragment

/**
 * [BaseFragment] subclass to show carousel
 *
 */
class MainFragment : BaseFragment() {

    private lateinit var pageAdapter: CarouselStatePageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pictures = listOf(
            R.drawable.img_carousel_1,
            R.drawable.img_carousel_2,
            R.drawable.img_carousel_3,
            R.drawable.img_carousel_4,
            R.drawable.img_carousel_5,
            R.drawable.img_carousel_6,
            R.drawable.img_carousel_7,
            R.drawable.img_carousel_8,
            R.drawable.img_carousel_9,
            R.drawable.img_carousel_10
        )
        pageAdapter = CarouselStatePageAdapter(childFragmentManager,pictures)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager_main_fragment.adapter = pageAdapter
    }

    companion object {
        fun newInstance() :MainFragment{
            return MainFragment()
        }
    }
}
