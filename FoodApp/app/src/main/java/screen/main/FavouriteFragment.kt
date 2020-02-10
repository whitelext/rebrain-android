package screen.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.FoodApplication
import com.example.foodapp.R
import di.DaggerMainActivityComponent
import di.ProductModule
import kotlinx.android.synthetic.main.fragment_favourite.*
import screen.main.rview.FavoriteListAdapter
import screen.main.viewmodel.ProductListViewModel
import utils.BaseFragment
import utils.Generator
import javax.inject.Inject

/**
 * A [Fragment] subclass for showing favourite food
 *
 */
class FavouriteFragment : BaseFragment() {

    private val favoriteListAdapter = FavoriteListAdapter()
    override fun getFragmentTag(): String {
        return TAG
    }

    @Inject
    lateinit var viewModel: ProductListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        val component =
            DaggerMainActivityComponent.builder()
                .appComponent(((this.activity!!.application) as FoodApplication).getAppComponent())
                .productModule(ProductModule(this, Generator))
                .build()
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRv(LinearLayoutManager(context))
        favoriteListAdapter.setFavoritesList(viewModel.getFavoritesList())
        favoriteListAdapter.notifyDataSetChanged()
        initSwipeToRefresh()
    }

    private fun initRv(lm: RecyclerView.LayoutManager) {
        with(recyclerView_favorites) {
            layoutManager = lm
            adapter = favoriteListAdapter
        }
    }

    private fun initSwipeToRefresh() {
        swipe_refresh_favorites.setProgressViewOffset(false, 200, 350)
        swipe_refresh_favorites.setColorSchemeResources(R.color.colorToolbar)
        swipe_refresh_favorites.setOnRefreshListener {
            favoriteListAdapter.setFavoritesList(viewModel.getFavoritesList())
            swipe_refresh_favorites.isRefreshing = false
        }
    }

    private fun initViewModel() {
        viewModel.favoriteList.observe(
            this,
            Observer { favoriteListAdapter.setFavoritesList(it) })
    }

    companion object {
        fun newInstance(): FavouriteFragment {
            return FavouriteFragment()
        }

        const val TAG = "FavoriteFragment"
    }
}
