package interactor.repositories

import domain.Product
import interactor.FavoriteListStorage
import javax.inject.Inject

/**
 * Repository for working with list of favorite products
 */
class FavoritesRepository @Inject constructor(private var favoriteListStorage: FavoriteListStorage) {
    /**
     * Returns list of  favorite products
     *
     */
    fun getFavoriteList() = favoriteListStorage.getElement()

    /**
     * Saving value of element to list
     *
     */
    fun saveElement(value: Product) = favoriteListStorage.saveElement(value)

    /**
     * Removing element from list
     *
     */
    fun removeElement(value: Product) = favoriteListStorage.removeElement(value)

}