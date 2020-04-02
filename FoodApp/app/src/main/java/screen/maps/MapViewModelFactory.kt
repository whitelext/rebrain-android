package screen.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import interactor.repositories.MapRepository
import javax.inject.Inject

/**
 * Factory for [MapViewModel]
 */
@Suppress("UNCHECKED_CAST")
class MapViewModelFactory @Inject constructor(
    private val mapRepository: MapRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(
            mapRepository
        ) as T
    }
}