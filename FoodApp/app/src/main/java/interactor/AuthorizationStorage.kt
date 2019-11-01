package interactor

import utils.SharedPreferencesHelper

const val authorizationKey = "isAuthorized" // Key for Authorization flag
/**
 * Storage for working with authorization flag
 */
class AuthorizationStorage(private val prefs: SharedPreferencesHelper) {
    /**
     * Returns true if User is authorized
     *
     */
    fun isUserAuthorized() = prefs.getBoolean(authorizationKey)
    /**
     * If [isUserAuthorized] make it false. And vice versa
     *
     */
    fun changeAuthorizationStatus() {
        prefs.putBoolean(authorizationKey,!isUserAuthorized())
    }
}