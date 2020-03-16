package di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import interactor.repositories.LoggedInUserRepository
import screen.main.viewmodel.ProfileViewModelFactory
import screen.main.viewmodel.ProfileViewModel

/**
 * Provides [ProfileViewModel] for profile screen
 *
 */
@Module
class ProfileFragmentModule(private val fragment: Fragment) {

    @Provides
    @PerScreen
    fun provideProfileViewModelFactory(
        loggedInUserRepository: LoggedInUserRepository
    ): ProfileViewModelFactory =
        ProfileViewModelFactory(loggedInUserRepository)

    @Provides
    @PerScreen
    fun provideProfileViewModel(profileViewModelFactory: ProfileViewModelFactory): ProfileViewModel =
        ViewModelProviders.of(
                fragment,
                profileViewModelFactory
            )
            .get(ProfileViewModel::class.java)

}