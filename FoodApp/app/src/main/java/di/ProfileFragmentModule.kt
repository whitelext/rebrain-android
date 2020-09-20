package di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.AuthorizationTokenRepository
import interactor.repositories.LoggedInUserRepository
import interactor.repositories.ProfileRepository
import network.auth.AuthApi
import network.user.UserApi
import screen.main.viewmodel.ProfileViewModel
import screen.main.viewmodel.ProfileViewModelFactory

/**
 * Provides [ProfileViewModel] for profile screen
 *
 */
@Module
class ProfileFragmentModule(private val fragment: Fragment) {

    @Provides
    @PerScreen
    fun provideProfileRepository(
        userApi: UserApi,
        authApi: AuthApi,
        authorizationTokenRepository: AuthorizationTokenRepository,
        context: Context
    ) = ProfileRepository(userApi, authApi, authorizationTokenRepository, context)

    @Provides
    @PerScreen
    fun provideProfileViewModelFactory(
        loggedInUserRepository: LoggedInUserRepository,
        authorizationFlagRepository: AuthorizationFlagRepository,
        profileRepository: ProfileRepository
    ): ProfileViewModelFactory =
        ProfileViewModelFactory(
            loggedInUserRepository,
            authorizationFlagRepository,
            profileRepository
        )

    @Provides
    @PerScreen
    fun provideProfileViewModel(profileViewModelFactory: ProfileViewModelFactory): ProfileViewModel =
        ViewModelProviders.of(
            fragment,
            profileViewModelFactory
        )
            .get(ProfileViewModel::class.java)

}