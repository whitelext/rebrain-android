package di

import dagger.Component
import interactor.AuthorizationFlagStorage
import interactor.IntroFlagStorage
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.IntroFlagRepository
import screen.intro.IntroActivity
import screen.intro.viewmodel.IntroViewModel
import screen.intro.viewmodel.IntroViewModelFactory
import screen.splash.SplashActivity

/**
 * Component for intro screen
 *
 */
@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [IntroModule::class]
)
interface IntroComponent {
    fun introFlagStorage(): IntroFlagStorage
    fun introFlagRepository(): IntroFlagRepository
    fun introViewModel(): IntroViewModel
    fun authorizationFlagStorage(): AuthorizationFlagStorage
    fun authorizationFlagRepository(): AuthorizationFlagRepository
    fun introViewModelFactory(): IntroViewModelFactory
    fun inject(splashActivity: SplashActivity)
    fun inject(introActivity: IntroActivity)
}