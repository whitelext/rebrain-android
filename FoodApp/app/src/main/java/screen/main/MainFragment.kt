package screen.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import di.AppModule
import di.DaggerMainFragmentComponent
import di.ProductModule
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.toast
import screen.main.rview.FoodListAdapter
import screen.main.rview.MarginItemDecoration
import screen.main.viewmodel.ProductListViewModel
import utils.BaseFragment
import utils.Generator
import javax.inject.Inject

/**
 * [BaseFragment] subclass to show carousel
 *
 */
class MainFragment : BaseFragment() {

    private val foodListAdapter = FoodListAdapter()
    private var lm = LinearLayoutManager(context)
    private val decor = MarginItemDecoration(11)
    @Inject
    lateinit var viewModel: ProductListViewModel

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        val component =
            DaggerMainFragmentComponent.builder().appModule(AppModule(this.activity!!.application))
                .productModule(ProductModule(this, Generator))
                .build()
        component.inject(this)
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
        initViewModel()
        foodListAdapter.buyButtonListener =
            { context: Context, id: String -> context.toast(id) }
        if (foodListAdapter.isGrid) {
            setGrid()
            recyclerView_main.addItemDecoration(decor)
        } else
            lm = LinearLayoutManager(context)
        initRv(lm)
        foodListAdapter.setProductList(viewModel.getProductList())
        initSwipeToRefresh()
        foodListAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_changerv, menu)
        menu.getItem(0)?.setIcon(
            if (!foodListAdapter.isGrid)
                R.drawable.ic_menu_grid
            else R.drawable.ic_menu_linear
        )
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (foodListAdapter.isGrid) {
            item.setIcon(R.drawable.ic_menu_grid)
            foodListAdapter.isGrid = false
            lm = LinearLayoutManager(context)
            initRv(lm)
            recyclerView_main.removeItemDecoration(decor)
        } else {
            item.setIcon(R.drawable.ic_menu_linear)
            foodListAdapter.isGrid = true
            recyclerView_main.addItemDecoration(decor)
            setGrid()
            initRv(lm)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRv(lm: RecyclerView.LayoutManager) {
        with(recyclerView_main) {
            layoutManager = lm
            adapter = foodListAdapter
        }
    }

    private fun initSwipeToRefresh() {
        swiperefresh.setProgressViewOffset(false, 200, 350)
        swiperefresh.setColorSchemeResources(R.color.colorToolbar)
        swiperefresh.setOnRefreshListener {
            foodListAdapter.setProductList(viewModel.shuffleProductList())
            swiperefresh.isRefreshing = false
        }
    }

    private fun setGrid() {
        lm = GridLayoutManager(context, 2)
        (lm as GridLayoutManager).spanSizeLookup = (object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (foodListAdapter.getItemViewType(position) == FoodListAdapter.MainTabRvType.VIEWPAGER.ordinal)
                    2
                else 1
            }
        })
    }

    private fun initViewModel() {
        viewModel.productList.observe(
            this,
            Observer { foodListAdapter.setProductList(it) })
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
        const val TAG = "MainFragment"
    }
}
