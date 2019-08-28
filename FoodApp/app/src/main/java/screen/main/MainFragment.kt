package screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import domain.Product
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import screen.main.carousel.adapter.CarouselStatePageAdapter
import screen.main.rview.FoodListAdapter
import utils.BaseFragment
import utils.Generator

/**
 * [BaseFragment] subclass to show carousel
 *
 */
class MainFragment : BaseFragment() {

    private lateinit var pageAdapter: CarouselStatePageAdapter
    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var lm: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
       initRv(rootView)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val pictures = listOf(
//            R.drawable.img_carousel_1,
//            R.drawable.img_carousel_2,
//            R.drawable.img_carousel_3,
//            R.drawable.img_carousel_4,
//            R.drawable.img_carousel_5,
//            R.drawable.img_carousel_6,
//            R.drawable.img_carousel_7,
//            R.drawable.img_carousel_8,
//            R.drawable.img_carousel_9,
//            R.drawable.img_carousel_10
//        )
//        pageAdapter = CarouselStatePageAdapter(childFragmentManager,pictures)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view_pager_main_fragment.adapter = pageAdapter
        foodListAdapter.setProductList(Generator.getProducts().toMutableList())

    }

    private fun initRv(rootView: View) {
        lm = LinearLayoutManager(context)
        foodListAdapter = FoodListAdapter()
        rootView.recyclerView.apply {
            layoutManager = lm
            adapter = foodListAdapter
        }
    }

    companion object {
        fun newInstance() :MainFragment{
            return MainFragment()
        }
    }
}
