package screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.AuthorizationTokenRepository
import interactor.repositories.LoginRepository
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 */
class LoginViewModelFactory @Inject constructor(
    private val loginRepository: LoginRepository,
    private val authorizationTokenRepository: AuthorizationTokenRepository,
    private val authorizationFlagRepository: AuthorizationFlagRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository,
                authorizationTokenRepository,
                authorizationFlagRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
