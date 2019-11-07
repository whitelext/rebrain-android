package screen.intro.viewmodel

import androidx.lifecycle.ViewModel
import interactor.repositories.IntroFlagRepository

/**
 * [ViewModel] for IntroActivity
 */
class IntroViewModel(
    private val introRepository: IntroFlagRepository
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
}