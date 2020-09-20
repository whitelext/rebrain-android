package screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.ProfileRepository
import javax.inject.Inject

/**
 * Factory for [ProfileViewModel]
 */
@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory @Inject constructor(
    private val loggedInUserRepository: LoggedInUserRepository,
    private val authorizationFlagRepository: AuthorizationFlagRepository,
    private val profileRepository: ProfileRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            loggedInUserRepository,
            authorizationFlagRepository,
            profileRepository
        ) as T
    }
}