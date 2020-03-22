package interactor

import utils.SharedPreferencesHelper
import interactor.utils.Storage
import javax.inject.Inject

const val introSharedPrefKey = "isIntroNeeded" // Key for Intro to work with Shared Preferences

/**
 * Storage for working with intro flag
 */
class IntroFlagStorage @Inject constructor(private val prefs: SharedPreferencesHelper) :
    Storage<Boolean> {
    /**
     * Returns true if intro was showed
     *
     */
    override fun getElement() = prefs.getBoolean(introSharedPrefKey)

    /**
     * Saving value of intro flag in shared preferences
     *
     */
    override fun saveElement(value: Boolean) = prefs.putBoolean(introSharedPrefKey, value)

}