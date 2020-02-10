package screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.Product
import interactor.repositories.FavoritesRepository
import interactor.repositories.ProductModeRepository
import interactor.repositories.ProductsRepository

/**
 * [ViewModel] for MainFragment
 */
class ProductListViewModel(
    private val productsRepository: ProductsRepository,
    private val productModeRepository: ProductModeRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

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
     * @return [MutableList] of [Product] or empty list
     */
    fun getFavoritesList(): MutableList<Product> = _favoriteList.value ?: mutableListOf()

    fun setFavoriteList(favorites: MutableList<Product>) {
        _favoriteList.value = favorites
    }

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
     * If Mode is Grid make it Linear. And vice versa
     *
     */
    fun switchDisplayMode() {
        productModeRepository.switchProductMode()
    }

    fun getCarouselPictures() = productsRepository.getCarouselPictures()
}