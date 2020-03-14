package interactor

import screen.login.LoggedInUser
import utils.Storage
import javax.inject.Inject

/**
 * Stores user details post authentication that is exposed to the UI
 *
 */
class LoggedInUserStorage @Inject constructor() : Storage<LoggedInUser> {
    var loggedInUser = LoggedInUser(displayName = "CurrentUserName")

    /**
     * Returns current logged in user
     *
     */
    override fun getElement(): LoggedInUser {
        return loggedInUser
    }

    /**
     * Saving logged user
     *
     */
    override fun saveElement(value: LoggedInUser) {
        loggedInUser = value
    }
}