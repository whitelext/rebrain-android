package screen.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whitelext.foodapp.R
import interactor.repositories.MapRepository
import kotlinx.coroutines.launch
import utils.Result

/**
 * [ViewModel] for map screen
 *
 */
class MapViewModel(private val mapRepository: MapRepository) : ViewModel() {
    private val _storeLoadingResult = MutableLiveData<StoreLoadingResult>()
    val storeLoadingResult: LiveData<StoreLoadingResult>
        get() = _storeLoadingResult

    /**
     * loading [List] of PickupPoints from server
     *
     */
    fun loadStores() {
        viewModelScope.launch {
            val response = mapRepository.getStoreLocations()
            _storeLoadingResult.value = when (response) {
                // Because the server temporarily send the same point
                is Result.Success -> {
                    response.data.forEach {
                        it.location.lat += (0..3).random()
                        it.location.long += (0..3).random()
                    }
                    StoreLoadingResult(response.data, false)
                }
                is Result.Error -> StoreLoadingResult(
                    isLoading = false,
                    error = R.string.pickup_loading_error
                )
            }
        }

    }
}