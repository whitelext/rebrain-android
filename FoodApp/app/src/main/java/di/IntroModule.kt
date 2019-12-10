package di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import interactor.repositories.IntroFlagRepository
import screen.intro.viewmodel.IntroViewModel
import screen.intro.viewmodel.IntroViewModelFactory

@Module
class IntroModule(private val activity: FragmentActivity) {
    @Provides
    @PerScreen
    fun provideIntroViewModelFactory(
        introFlagRepository: IntroFlagRepository
    ): IntroViewModelFactory =
        IntroViewModelFactory(introFlagRepository)

    @Provides
    @PerScreen
    fun provideIntroViewModel(introViewModelFactory: IntroViewModelFactory): IntroViewModel =
        ViewModelProviders.of(
            activity,
            introViewModelFactory
        )
            .get(IntroViewModel::class.java)
}