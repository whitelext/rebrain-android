package interactor

import utils.SharedPreferencesHelper

/**
 * Storage for working with authorization flag
 */
class AuthorizationStorage(private val prefs: SharedPreferencesHelper) {
    /**
     * Returns true if User is authorized
     *
     */
    fun isUserAuthorized() = prefs.isUserAuthorized
    /**
     * If [isUserAuthorized] make it false. And vice versa
     *
     */
    fun changeAuthorizationStatus() {
        prefs.isUserAuthorized = !prefs.isUserAuthorized
    }
}