package interactor

import utils.SharedPreferencesHelper

/**
 * Storage for Product display mode
 */
class ProductModeStorage(private val prefs : SharedPreferencesHelper){
    /**
     * Returns true if display mode is Grid
     *
     */
    fun isModeGrid() = prefs.isModeGrid

    /**
     * If [isModeGrid] make it false. And vice versa
     *
     */
    fun switchCurrentMode(){
        prefs.isModeGrid = !prefs.isModeGrid
    }
}