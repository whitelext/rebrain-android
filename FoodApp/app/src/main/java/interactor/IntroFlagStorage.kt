package interactor

import utils.SharedPreferencesHelper
import utils.Storage

const val introSharedPrefKey = "isIntroNeeded" // Key for Intro to work with Shared Preferences

/**
 * Storage for working with intro flag
 */
class IntroFlagStorage(private val prefs: SharedPreferencesHelper) : Storage {
    /**
     * Returns true if intro was showed
     *
     */
    override fun getElement() = prefs.getBoolean(introSharedPrefKey)

    /**
     * Saving value of intro flag in shared preferences
     *
     */
    override fun saveElement(value:Boolean) = prefs.putBoolean(introSharedPrefKey, value)

}