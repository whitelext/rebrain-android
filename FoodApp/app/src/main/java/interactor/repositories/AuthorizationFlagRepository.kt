package interactor.repositories

import interactor.AuthorizationStorage
/**
* Repository for working with authorization flag
*/
class AuthorizationFlagRepository(private val authorizationStatus: AuthorizationStorage) {
    /**
     * Returns true if User is authorized
     *
     */
    fun isUserAuthorized() = authorizationStatus.isUserAuthorized()
    /**
     * If [isUserAuthorized] make it false. And vice versa
     *
     */
    fun changeAuthorizationStatus() = authorizationStatus.changeAuthorizationStatus()
}