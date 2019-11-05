package interactor

import utils.SharedPreferencesHelper

const val displayModeKey = "isGrid" // Key for display mode
/**
 * Storage for Product display mode
 */
class ProductModeStorage(private val prefs : SharedPreferencesHelper){
    /**
     * Returns true if display mode is Grid
     *
     */
    fun isModeGrid() = prefs.getBoolean(displayModeKey)

    /**
     * If [isModeGrid] make it false. And vice versa
     *
     */
    fun switchCurrentMode(){
        prefs.putBoolean(displayModeKey,!isModeGrid())
    }
}