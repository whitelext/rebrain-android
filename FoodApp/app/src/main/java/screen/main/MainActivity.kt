package screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import utils.BaseActivity
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_main.*
import screen.main.carousel.adapter.CarouselStatePageAdapter

/**
 *The Main Screen of application
 *
 * For now it shows list of jpg images in a ViewPager
 */
class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        // You can also use CarouselPageAdapter
        val pageAdapter = CarouselStatePageAdapter(supportFragmentManager,pictures)
        val viewPager = view_pager_main
        viewPager.adapter = pageAdapter
    }
    companion object{
        fun start(context: Context){
            startActivity(context,Intent(context, MainActivity::class.java),null)
        }
    }
}
