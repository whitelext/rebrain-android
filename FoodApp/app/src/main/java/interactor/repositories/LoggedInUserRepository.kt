package interactor.repositories

import interactor.LoggedInUserStorage
import screen.login.LoggedInUser
import javax.inject.Inject

/**
 * Repository for working with logged user data
 *
 */
class LoggedInUserRepository @Inject constructor(private val loggedInUserStorage: LoggedInUserStorage) {
    /**
     * Returns current logged user
     *
     */
    fun getLoggedUser() = loggedInUserStorage.getElement()

    /**
     * Change current logged user
     *
     */
    fun setLoggedUser(user: LoggedInUser) = loggedInUserStorage.saveElement(user)
}