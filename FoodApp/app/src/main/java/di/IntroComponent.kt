package di

import dagger.Component
import interactor.repositories.AuthorizationFlagRepository
import screen.intro.IntroActivity
import screen.intro.viewmodel.IntroViewModel
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
    fun introViewModel(): IntroViewModel
    fun authorizationFlagRepository(): AuthorizationFlagRepository
    fun inject(splashActivity: SplashActivity)
    fun inject(introActivity: IntroActivity)
}