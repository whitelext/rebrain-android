package screen.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerMainFragmentComponent
import di.ProductModule
import kotlinx.android.synthetic.main.fragment_main.*
import okhttp3.OkHttpClient
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

    private var lm = LinearLayoutManager(context)
    private val decor = MarginItemDecoration(11)

    @Inject
    lateinit var viewModel: ProductListViewModel

    @Inject
    lateinit var client: OkHttpClient


    private val foodListAdapter = FoodListAdapter { context: Context, id: Int ->
        context.toast("$id")
        viewModel.addFavorite(id)
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        val component =
            DaggerMainFragmentComponent.builder()
                .appComponent(((this.requireActivity().application) as FoodApplication).getAppComponent())
                .productModule(ProductModule(this, Generator))
                .build()
        component.inject(this)
        retainInstance = true
        viewModel.loadCarouselBanners()
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
        if (foodListAdapter.isGrid) {
            setGrid()
            recyclerView_main.addItemDecoration(decor)
        } else
            lm = LinearLayoutManager(context)
        initRv(lm)
        foodListAdapter.setProductList(viewModel.getProductList())
        viewModel.makeServerRequest(client)
        initSwipeToRefresh()
    }

    override fun onPause() {
        foodListAdapter.productClickDisposable.dispose()
        super.onPause()
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
        swipe_refresh_main.setProgressViewOffset(false, 200, 350)
        swipe_refresh_main.setColorSchemeResources(R.color.colorToolbar)
        swipe_refresh_main.setOnRefreshListener {
            foodListAdapter.setProductList(viewModel.shuffleProductList())
            viewModel.makeServerRequest(client)
            swipe_refresh_main.isRefreshing = false
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
            viewLifecycleOwner,
            Observer { foodListAdapter.setProductList(it) })

        viewModel.bannerLoadingResult.observe(viewLifecycleOwner, Observer { loadingResult ->

            loadingResult.error?.let {
                Snackbar.make(recyclerView_main, R.string.banner_upload_error, Snackbar.LENGTH_LONG)
                    .show()
            }

            loadingResult.success?.let {
                foodListAdapter.setBannerList(it)
            }
        })
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }

        const val TAG = "MainFragment"
    }
}
