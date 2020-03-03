package screen.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerFavoriteFragmentComponent
import di.FavoriteFragmentModule
import kotlinx.android.synthetic.main.fragment_favourite.*
import screen.main.rview.FavoriteListAdapter
import screen.main.viewmodel.FavoriteListViewModel
import utils.BaseFragment
import javax.inject.Inject

/**
 * A [Fragment] subclass for showing favourite food
 *
 */
class FavouriteFragment : BaseFragment() {

    private val favoriteListAdapter = FavoriteListAdapter { id: Int ->
        viewModel.removeElement(id)
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    @Inject
    lateinit var viewModel: FavoriteListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        val component =
            DaggerFavoriteFragmentComponent.builder()
                .appComponent(((this.requireActivity().application) as FoodApplication).getAppComponent())
                .favoriteFragmentModule(FavoriteFragmentModule(this))
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
            viewLifecycleOwner,
            Observer { favoriteListAdapter.setFavoritesList(it) })
    }

    companion object {
        fun newInstance(): FavouriteFragment {
            return FavouriteFragment()
        }

        const val TAG = "FavoriteFragment"
    }
}
