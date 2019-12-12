package di

import dagger.Component
import interactor.IntroFlagStorage
import interactor.repositories.IntroFlagRepository
import screen.intro.viewmodel.IntroViewModel
import screen.intro.viewmodel.IntroViewModelFactory
import screen.splash.SplashActivity

@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [IntroModule::class]
)
interface IntroComponent {
    fun introFlagStorage(): IntroFlagStorage
    fun introFlagRepository(): IntroFlagRepository
    fun introViewModel(): IntroViewModel
    fun introViewModelFactory(): IntroViewModelFactory
    fun inject(splashActivity: SplashActivity)
}