package di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import interactor.repositories.LoginRepository
import screen.login.LoginViewModel
import screen.login.LoginViewModelFactory

/**
 * Provides [LoginViewModel] for login screen
 *
 */
@Module
class LoginActivityModule(private val activity: FragmentActivity) {
    @Provides
    @PerScreen
    fun provideLoginViewModelFactory(
        loginRepository: LoginRepository
    ): LoginViewModelFactory =
        LoginViewModelFactory(
            loginRepository
        )

    @Provides
    @PerScreen
    fun provideLoginViewModel(loginViewModelFactory: LoginViewModelFactory): LoginViewModel =
        ViewModelProviders.of(
            activity,
            loginViewModelFactory
        )
            .get(LoginViewModel::class.java)
}