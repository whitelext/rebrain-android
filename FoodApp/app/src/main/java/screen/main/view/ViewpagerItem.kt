package screen.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.whitelext.foodapp.R
import domain.Banner
import kotlinx.android.synthetic.main.carousel_item.view.*
import screen.main.carousel.adapter.CarouselStatePageAdapter
import screen.main.rview.FoodListAdapter

class ViewpagerItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val fm: FragmentManager = (context as FragmentActivity).supportFragmentManager
    private var bannerList = mutableListOf<Banner>()

    init {
        inflate(context, R.layout.carousel_item, this)
    }

    fun setupListener(adapter: FoodListAdapter) {
        carousel_element_tab_pager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                adapter.carouselCheckedItem = position
            }
        })
    }

    fun setBannerImages(list: MutableList<Banner>) {
        bannerList = list
        setupViewpager()
    }

    fun setItem(item: Int) {
        carousel_element_tab_pager.setCurrentItem(item, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setupViewpager()
    }

    private fun setupViewpager() {
        carousel_element_tab_pager.adapter = CarouselStatePageAdapter(fm, bannerList)
        carousel_element_tab_layout.setupWithViewPager(carousel_element_tab_pager, false)
    }
}