package interactor.repositories

import interactor.ProductModeStorage
import javax.inject.Inject

/**
 * Repository for working wit Product display mode
 */
class ProductModeRepository @Inject constructor(private var productMode: ProductModeStorage) {
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