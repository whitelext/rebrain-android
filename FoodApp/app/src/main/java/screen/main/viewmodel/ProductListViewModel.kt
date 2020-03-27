package screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitelext.foodapp.R
import domain.Product
import interactor.repositories.BannerRepository
import interactor.repositories.FavoritesRepository
import interactor.repositories.ProductModeRepository
import interactor.repositories.ProductsRepository
import kotlinx.coroutines.launch
import okhttp3.*
import screen.main.BannerLoadingResult
import timber.log.Timber
import utils.Result
import java.io.IOException

/**
 * [ViewModel] for MainFragment
 */
class ProductListViewModel(
    private val productsRepository: ProductsRepository,
    private val productModeRepository: ProductModeRepository,
    private val favoritesRepository: FavoritesRepository,
    private val bannerRepository: BannerRepository
) : ViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    private val _bannerLoadingResult = MutableLiveData<BannerLoadingResult>()
    val bannerLoadingResult: LiveData<BannerLoadingResult>
        get() = _bannerLoadingResult

    private val _isListGrid = MutableLiveData<Boolean>()
    val isListGrid: LiveData<Boolean>
        get() = _isListGrid

    private val _favoriteList = MutableLiveData<MutableList<Product>>()
    val favoriteList: LiveData<MutableList<Product>>
        get() = _favoriteList

    init {
        _productList.value = productsRepository.getProductList()
        _isListGrid.value = productModeRepository.isModeGrid()
        _favoriteList.value = favoritesRepository.getFavoriteList()
    }

    /**
     * @return [List] of [Product] or empty list
     */
    fun getProductList(): List<Product> = _productList.value ?: listOf()

    /**
     * @return true if display mode is Grid
     */
    fun isModeGrid(): Boolean = _isListGrid.value ?: false

    /**
     * Shuffles productList
     * @return shuffled [List] of [Product]
     */
    fun shuffleProductList(): List<Product> {
        return productsRepository.getProductList().shuffled()
    }

    /**
     * Adds a product to list of favorites
     * @param id is product id
     */
    fun addFavorite(id: Int) {
        val product = productList.value?.find {
            it.id == id
        }
        product?.let {
            if (!favoritesRepository.getFavoriteList().contains(it))
                favoritesRepository.saveElement(it)
        }
    }

    /**
     * If Mode is Grid make it Linear. And vice versa
     *
     */
    fun switchDisplayMode() {
        productModeRepository.switchProductMode()
    }

    /**
     * Loading banners from Firebase Storage
     *
     */
    fun loadCarouselBanners() {
        viewModelScope.launch {
            _bannerLoadingResult.value = BannerLoadingResult(isLoading = true)
            val response = bannerRepository.getBanners()
            _bannerLoadingResult.value = when (response) {
                is Result.Success -> BannerLoadingResult(response.data, isLoading = false)
                is Result.Error -> BannerLoadingResult(
                    error = R.string.banner_upload_error,
                    isLoading = false
                )
            }
        }
    }

    /**
     *  Server request
     *
     */
    fun makeServerRequest(client: OkHttpClient) {
        val request = Request.Builder()
            .url("http://api.android.srwx.net/api/v2")
            .build()
        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Timber.tag("Network").e(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    Timber.tag("Network").i(response.body.toString())
                }
            })
        } catch (e: Exception) {
            Timber.tag("Network").e(e)
        }
    }

}