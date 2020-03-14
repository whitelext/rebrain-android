package utils

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject

/**
 * Helper class to work with SharedPreferences
 *
 * @param context Context of Activity
 */
class SharedPreferencesHelper @Inject constructor(context: Context) {
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Puts boolean [value]  of [key] to sharedPrefs
     *
     */
    fun putBoolean(key: String, value: Boolean) = sharedPref.edit().putBoolean(key, value).apply()

    /**
     * Returns boolean [value]  of [key] from sharedPrefs
     *
     */
    fun getBoolean(key: String) = sharedPref.getBoolean(key, false)

    /**
     * Puts string [value]  of [key] to sharedPrefs
     *
     */
    fun putString(key: String, value: String) = sharedPref.edit().putString(key, value).apply()

    /**
     * Returns string [value]  of [key] from sharedPrefs
     *
     */
    fun getString(key: String) = sharedPref.getString(key, "noStringFound")


}