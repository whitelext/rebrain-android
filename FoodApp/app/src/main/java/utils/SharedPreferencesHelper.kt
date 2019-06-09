package utils

import android.content.Context
import android.preference.PreferenceManager

const val introSharedPrefKey = "isIntroNeeded" // Key for Intro to work with Shared Preferences
/**
 * Helper class to work with SharedPreferences
 *
 * @param context Context of Activity
 *
 * @property isIntroNeeded Is for storing information do we need to show IntroActivity
 */
class SharedPreferencesHelper(context : Context) {
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    var isIntroNeeded: Boolean
        get() = sharedPref.getBoolean(introSharedPrefKey,true)
        set(value) = sharedPref.edit().putBoolean(introSharedPrefKey, value).apply()

}