package screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import interactor.repositories.FavoritesRepository
import javax.inject.Inject

/**
 * Factory for [FavoriteListViewModel]
 */
@Suppress("UNCHECKED_CAST")
class FavoriteListViewModelFactory @Inject constructor(
    private val favoritesRepository: FavoritesRepository

) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteListViewModel(
            favoritesRepository
        ) as T
    }
}