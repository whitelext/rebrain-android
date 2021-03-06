package screen.main.carousel.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import domain.Banner
import screen.main.carousel.CarouselFragment

/**
 * Adapter for ViewPager
 */
class CarouselStatePageAdapter(
    fragmentManager: FragmentManager,
    private val pictures: List<Banner>
) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(i: Int): Fragment {
        return CarouselFragment.newInstance(pictures[i].imageUrl)
    }

    override fun getCount(): Int {
        return pictures.size
    }
}