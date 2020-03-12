package interactor.repositories

import interactor.LoggedInUserStorage
import screen.login.LoggedInUser
import javax.inject.Inject

/**
 * Repository for working with logged user data
 *
 */
class LoggedInUserRepository @Inject constructor(private val loggedInUserStorage: LoggedInUserStorage) {
    fun getLoggedUser() = loggedInUserStorage.loggedInUser

    fun setLoggedUser(user: LoggedInUser) = loggedInUserStorage.setLoggedUser(user)
}