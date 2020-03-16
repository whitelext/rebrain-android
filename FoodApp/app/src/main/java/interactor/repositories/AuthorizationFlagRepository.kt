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
     * Saving true to login flag
     *
     */
    fun loginUser() = authorizationStatus.saveElement(true)

    /**
     * Saving false to login flag
     *
     */
    fun logoutUser() = authorizationStatus.saveElement(false)
}