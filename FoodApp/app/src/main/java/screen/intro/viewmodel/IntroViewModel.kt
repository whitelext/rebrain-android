package screen.intro.viewmodel

import androidx.lifecycle.ViewModel
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.IntroFlagRepository

/**
 * [ViewModel] for IntroActivity
 */
class IntroViewModel(
    private val introRepository: IntroFlagRepository,
    private val authorizationFlagRepository: AuthorizationFlagRepository
) : ViewModel() {
    /**
     * Returns true if intro was showed
     *
     */
    fun isIntroShowed() = introRepository.isIntroShowed()

    /**
     * Change value of [isIntroShowed] to true
     *
     */
    fun showIntro() = introRepository.showIntro()

    /**
     * Checks if user is authorized
     *
     */
    fun isUserAuthorized() = authorizationFlagRepository.isUserAuthorized()
}