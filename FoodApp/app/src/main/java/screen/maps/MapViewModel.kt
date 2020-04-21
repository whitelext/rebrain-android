package screen.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whitelext.foodapp.R
import interactor.repositories.MapRepository
import io.reactivex.disposables.CompositeDisposable
import utils.BaseViewModel

/**
 * [ViewModel] for map screen
 *
 */
class MapViewModel(private val mapRepository: MapRepository) : BaseViewModel() {
    private val disposables = CompositeDisposable()
    private val _storeLoadingResult = MutableLiveData<StoreLoadingResult>()
    val storeLoadingResult: LiveData<StoreLoadingResult>
        get() = _storeLoadingResult

    /**
     * loading [List] of PickupPoints from server
     *
     */
    fun loadStores() {
        disposables.add(
            mapRepository.getStoreLocations()
                .subscribeToRequest(onNext = { pickupList ->
                    _storeLoadingResult.value =
                        StoreLoadingResult(
                            pickupList,
                            isLoading = false
                        )
                }, onError = {
                    _storeLoadingResult.value =
                        StoreLoadingResult(isLoading = false, error = R.string.pickup_loading_error)
                })
        )
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}