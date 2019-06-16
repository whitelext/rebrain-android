package screen.main.carousel.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import screen.main.carousel.CarouselFragment

/**
 * Adapter for ViewPager
 */
class CarouselStatePageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private val pictures = mutableListOf<Int>()

    override fun getItem(i: Int): Fragment {
        return CarouselFragment.newInstance(pictures[i])
    }

    override fun getCount(): Int {
        return pictures.size
    }

    fun addPictures(list: List<Int>) {
        pictures.addAll(list)
    }
}