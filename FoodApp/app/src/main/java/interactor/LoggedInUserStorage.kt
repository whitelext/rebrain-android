package interactor

import interactor.utils.Storage
import screen.login.LoggedInUser
import javax.inject.Inject

/**
 * Stores user details post authentication that is exposed to the UI
 *
 */
class LoggedInUserStorage @Inject constructor() :
    Storage<LoggedInUser> {
    private var loggedInUser = LoggedInUser(displayName = "CurrentUserName",displayAvatar = null)

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