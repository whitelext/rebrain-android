package di

import dagger.Component
import screen.main.ProfileFragment
import screen.main.viewmodel.ProfileViewModel

/**
 * Component for [ProfileFragment]
 *
 */
@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [ProfileFragmentModule::class]
)
interface ProfileFragmentComponent {
    fun profileViewModel(): ProfileViewModel
    fun inject(profileFragment: ProfileFragment)
}