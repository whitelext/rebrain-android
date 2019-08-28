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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
