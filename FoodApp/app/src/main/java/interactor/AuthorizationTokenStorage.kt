package interactor

import utils.SharedPreferencesHelper
import utils.Storage
import javax.inject.Inject

const val authorizationKey = "AuthorizationToken"

/**
 * Storage for working with authorization token
 */
class AuthorizationTokenStorage @Inject constructor(private val prefs: SharedPreferencesHelper) :
    Storage<String> {
    /**
     * Returns current authorization token from shared preferences
     *
     */
    override fun getElement() = prefs.getString(authorizationKey)

    /**
     * Saves authorization token to shared preferences
     *
     */
    override fun saveElement(value: String) = prefs.putString(authorizationKey, value)

}