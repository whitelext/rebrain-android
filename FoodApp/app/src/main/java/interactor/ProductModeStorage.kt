package interactor

import utils.SharedPreferencesHelper
import interactor.utils.Storage
import javax.inject.Inject

const val displayModeKey = "isGrid" // Key for display mode

/**
 * Storage for Product display mode
 */
class ProductModeStorage @Inject constructor(private val prefs: SharedPreferencesHelper) :
    Storage<Boolean> {
    /**
     * Returns true if display mode is Grid
     *
     */
    override fun getElement() = prefs.getBoolean(displayModeKey)

    /**
     * Saving value of display mode flag in shared preferences
     *
     */
    override fun saveElement(value: Boolean) = prefs.putBoolean(displayModeKey, value)

}