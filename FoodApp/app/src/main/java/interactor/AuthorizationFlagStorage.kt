package interactor

import utils.SharedPreferencesHelper
import interactor.utils.Storage
import javax.inject.Inject

const val authorizationFlagKey = "isAuthorized" // Key for Authorization flag

/**
 * Storage for working with authorization flag
 */
class AuthorizationFlagStorage @Inject constructor(private val prefs: SharedPreferencesHelper) :
    Storage<Boolean> {
    /**
     * Returns true if User is authorized
     *
     */
    override fun getElement() = prefs.getBoolean(authorizationFlagKey)

    /**
     * Saving value of authorization flag in shared preferences
     *
     */
    override fun saveElement(value: Boolean) = prefs.putBoolean(authorizationFlagKey, value)

}