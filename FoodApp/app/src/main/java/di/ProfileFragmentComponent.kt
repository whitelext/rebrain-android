package di

import dagger.Component
import di.network.api.AuthApiModule
import di.network.api.UserApiModule
import screen.main.ProfileFragment
import screen.main.viewmodel.ProfileViewModel

/**
 * Component for [ProfileFragment]
 *
 */
@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [ProfileFragmentModule::class,
        UserApiModule::class,
        AuthApiModule::class]
)
interface ProfileFragmentComponent {
    fun profileViewModel(): ProfileViewModel
    fun inject(profileFragment: ProfileFragment)
}