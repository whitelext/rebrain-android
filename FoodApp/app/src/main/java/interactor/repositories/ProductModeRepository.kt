package interactor.repositories

import interactor.ProductModeStorage

/**
 * Repository for working wit Product display mode
 */
class ProductModeRepository(private val productMode: ProductModeStorage) {
    /**
     * Returns true if display mode is Grid
     *
     */
    fun isModeGrid() = productMode.isModeGrid()

    /**
     * If [isModeGrid] make it false. And vice versa
     *
     */
    fun switchProductMode() = productMode.switchCurrentMode()
}