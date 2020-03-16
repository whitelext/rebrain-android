package interactor.repositories

import interactor.AuthorizationTokenStorage
import javax.inject.Inject

/**
 * Repository for working with authorization token
 */
class AuthorizationTokenRepository @Inject constructor(private var authorizationTokenStorage: AuthorizationTokenStorage) {
    /**
     * Returns authorization token
     *
     */
    fun getAuthroizationToken() = authorizationTokenStorage.getElement()

    /**
     * Saves token to shared preferences
     *
     */
    fun saveAuthorizationToken(token: String) = authorizationTokenStorage.saveElement(token)
}