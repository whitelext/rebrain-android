package utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Helper class to work with SharedPreferences
 *
 * @param context Context of Activity
 */
class SharedPreferencesHelper(context: Context) {
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    fun putBoolean(key: String, value: Boolean) = sharedPref.edit().putBoolean(key, value).apply()

    fun getBoolean(key: String) = sharedPref.getBoolean(key, false)
}