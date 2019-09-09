package screen.main

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import kotlinx.android.synthetic.main.fragment_main.*
import screen.main.rview.FoodListAdapter
import screen.main.rview.MarginItemDecoration
import utils.BaseFragment
import utils.Generator

/**
 * [BaseFragment] subclass to show carousel
 *
 */
class MainFragment : BaseFragment() {

    private lateinit var foodListAdapter: FoodListAdapter
    private var lm = LinearLayoutManager(context)
    private val decor = MarginItemDecoration(11)

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodListAdapter = FoodListAdapter()
        initRv(lm)
        foodListAdapter.setProductList(Generator.getProducts().toMutableList())
        initSwipeToRefresh()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_changerv, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (foodListAdapter.isGrid) {
            item?.setIcon(R.drawable.ic_menu_grid)
            foodListAdapter.isGrid = false
            lm = LinearLayoutManager(context)
            initRv(lm)
            recyclerView_main.removeItemDecoration(decor)
        } else {
            item?.setIcon(R.drawable.ic_menu_linear)
            foodListAdapter.isGrid = true
            recyclerView_main.addItemDecoration(decor)
            lm = GridLayoutManager(context, 2)
            initRv(lm)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRv(lm:RecyclerView.LayoutManager) {
       with(recyclerView_main) {
            layoutManager = lm
            adapter = foodListAdapter
        }
    }

    private fun initSwipeToRefresh() {
        swiperefresh.setProgressViewOffset(false, 150, 250)
        swiperefresh.setColorSchemeResources(R.color.colorToolbar)
        swiperefresh.setOnRefreshListener {
            foodListAdapter.setProductList(Generator.getProducts().toMutableList())
            swiperefresh.isRefreshing = false
        }
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
