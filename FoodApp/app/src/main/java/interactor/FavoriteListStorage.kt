package interactor

import domain.Product
import javax.inject.Inject

class FavoriteListStorage @Inject constructor() {

    private val favoriteList: MutableList<Product> = mutableListOf()
    /**
     * Returns list of favorite products
     *
     */
    fun getElement() = favoriteList

    /**
     * Saving value of element to list
     *
     */
    fun saveElement(value: Product) = favoriteList.add(value)

    /**
     * Removing element from list
     *
     */
    fun removeElement(value: Product) = favoriteList.remove(value)

}