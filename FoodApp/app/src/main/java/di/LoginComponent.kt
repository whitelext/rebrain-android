package di

import dagger.Component
import di.network.api.AuthApiModule
import screen.login.LoginActivity
import screen.login.LoginViewModel

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
    fun loginViewModel(): LoginViewModel
    fun inject(loginActivity: LoginActivity)
}