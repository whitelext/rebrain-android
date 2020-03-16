package screen.intro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import interactor.repositories.AuthorizationFlagRepository
import interactor.repositories.IntroFlagRepository
import javax.inject.Inject

/**
 * Factory for [IntroViewModel]
 */
@Suppress("UNCHECKED_CAST")
class IntroViewModelFactory @Inject constructor(
    private val introRepository: IntroFlagRepository,
    private val authorizationFlagRepository: AuthorizationFlagRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IntroViewModel(introRepository, authorizationFlagRepository) as T
    }
}