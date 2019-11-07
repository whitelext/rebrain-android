package interactor.repositories

import utils.Storage

/**
 * Repository for working with authorization flag
 */
class AuthorizationFlagRepository(private val authorizationStatus: Storage) {
    /**
     * Returns true if User is authorized
     *
     */
    fun isUserAuthorized() = authorizationStatus.getElement()

    /**
     * If [isUserAuthorized] make it false. And vice versa
     *
     */
    fun changeAuthorizationStatus() = authorizationStatus.saveElement(!isUserAuthorized())
}