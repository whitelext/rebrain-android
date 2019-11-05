package interactor

import utils.SharedPreferencesHelper
import utils.Storage

const val authorizationKey = "isAuthorized" // Key for Authorization flag

/**
 * Storage for working with authorization flag
 */
class AuthorizationStorage(private val prefs: SharedPreferencesHelper) : Storage {
    /**
     * Returns true if User is authorized
     *
     */
    override fun getElement() = prefs.getBoolean(authorizationKey)

    /**
     * Saving value of authorization flag in shared preferences
     *
     */
    override fun saveElement(value:Boolean) = prefs.putBoolean(authorizationKey, value)

}