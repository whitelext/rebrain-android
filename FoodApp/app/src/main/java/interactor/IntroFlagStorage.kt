package interactor

import utils.SharedPreferencesHelper

const val introSharedPrefKey = "isIntroNeeded" // Key for Intro to work with Shared Preferences

/**
 * Storage for working with intro flag
 */
class IntroFlagStorage(private val prefs: SharedPreferencesHelper) {
    /**
     * Returns true if intro was showed
     *
     */
    fun isIntroShowed() = prefs.getBoolean(introSharedPrefKey)

    /**
     * Change value of [isIntroShowed] to true
     *
     */
    fun showIntro() {
        prefs.putBoolean(introSharedPrefKey, true)
    }
}