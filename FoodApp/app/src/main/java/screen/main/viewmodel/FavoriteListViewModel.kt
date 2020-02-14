package screen.main.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.Product
import interactor.repositories.FavoritesRepository

/**
 * [ViewModel] for FavoriteFragment
 */
class FavoriteListViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favoriteList = MutableLiveData<MutableList<Product>>()
    val favoriteList: LiveData<MutableList<Product>>
        get() = _favoriteList

    init {
        _favoriteList.value = favoritesRepository.getFavoriteList()
    }

    /**
     * @return [MutableList] of [Product] or empty list
     */
    fun getFavoritesList(): MutableList<Product> = _favoriteList.value ?: mutableListOf()

    /**
     * Removes element from list of favorites
     *
     * @param id is product id
     */
    fun removeElement(id: Int) {
        val favorites = favoritesRepository.getFavoriteList()
        val favorite = favorites.find {
            it.id == id
        }
        favorite?.let {
            favoritesRepository.removeElement(it)
        }
    }


}