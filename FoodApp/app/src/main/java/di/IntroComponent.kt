package di

import android.content.Context
import dagger.Component
import interactor.IntroFlagStorage
import interactor.repositories.IntroFlagRepository
import screen.intro.viewmodel.IntroViewModel
import screen.intro.viewmodel.IntroViewModelFactory
import screen.splash.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [IntroModule::class,
        AppModule::class,
        IntroFlagModule::class]
)
interface IntroComponent {
    fun introFlagStorage(): IntroFlagStorage
    fun introFlagRepository(): IntroFlagRepository
    fun introViewModel(): IntroViewModel
    fun appContext(): Context
    fun introViewModelFactory(): IntroViewModelFactory
    fun inject(splashActivity: SplashActivity)
}