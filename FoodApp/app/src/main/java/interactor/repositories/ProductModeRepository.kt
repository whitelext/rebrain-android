package interactor.repositories

import interactor.ProductModeStorage
import utils.Storage

/**
 * Repository for working wit Product display mode
 */
class ProductModeRepository(private val productMode: Storage) {
    /**
     * Returns true if display mode is Grid
     *
     */
    fun isModeGrid() = productMode.getElement()

    /**
     * If [isModeGrid] make it false. And vice versa
     *
     */
    fun switchProductMode() = productMode.saveElement(!isModeGrid())
}