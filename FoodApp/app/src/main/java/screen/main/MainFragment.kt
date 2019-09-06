package screen.main

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
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
    private lateinit var lm: RecyclerView.LayoutManager
    private lateinit var rv: View
    val decor = MarginItemDecoration(11)

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rv = inflater.inflate(R.layout.fragment_main, container, false)
        foodListAdapter = FoodListAdapter()
        initRv(rv)
        return rv
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            initRv(rv)
            rv.recyclerView.removeItemDecoration(decor)
        } else {
            item?.setIcon(R.drawable.ic_menu_linear)
            foodListAdapter.isGrid = true
            initRv(rv)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRv(rootView: View) {
        if (foodListAdapter.isGrid) {
            lm = GridLayoutManager(context, 2)
            rootView.recyclerView.addItemDecoration(decor)
        } else {
            lm = LinearLayoutManager(context)
        }
        rootView.recyclerView.apply {
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
