package screen.intro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import interactor.repositories.IntroFlagRepository

/**
 * Factory for [IntroViewModel]
 */
class IntroViewModelFactory(
    private val introRepository: IntroFlagRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IntroViewModel(introRepository) as T
    }
}