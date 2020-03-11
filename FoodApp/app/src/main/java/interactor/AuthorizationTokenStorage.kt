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

    override fun getElement() = prefs.getString(authorizationKey)

    override fun saveElement(value: String) = prefs.putString(authorizationKey, value)

}