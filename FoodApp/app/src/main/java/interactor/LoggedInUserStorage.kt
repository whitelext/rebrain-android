package interactor

import screen.login.LoggedInUser
import javax.inject.Inject

/**
 * Stores user details post authentication that is exposed to the UI
 *
 */
class LoggedInUserStorage @Inject constructor() {
    var loggedInUser = LoggedInUser(displayName = "CurrentUserName")

    fun setLoggedUser(user: LoggedInUser) {
        loggedInUser = user
    }
}