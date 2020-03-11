package di

import dagger.Component
import di.network.api.AuthApiModule
import interactor.repositories.LoginRepository
import network.auth.AuthApi
import screen.login.LoginActivity
import screen.login.LoginViewModel
import screen.login.LoginViewModelFactory

/**
 * Component for login screen
 *
 */
@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [LoginActivityModule::class,
        AuthApiModule::class]
)
interface LoginComponent {
    fun loginRepository(): LoginRepository
    fun loginViewModelFactory(): LoginViewModelFactory
    fun loginViewModel(): LoginViewModel
    fun authApi(): AuthApi
    fun inject(loginActivity: LoginActivity)
}