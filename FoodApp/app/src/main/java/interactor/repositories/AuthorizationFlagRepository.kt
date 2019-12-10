package interactor.repositories

import interactor.AuthorizationFlagStorage
import javax.inject.Inject

/**
 * Repository for working with authorization flag
 */
class AuthorizationFlagRepository @Inject constructor(private var authorizationStatus: AuthorizationFlagStorage) {
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