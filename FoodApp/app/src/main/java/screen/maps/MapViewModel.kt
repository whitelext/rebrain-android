package screen.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.whitelext.foodapp.R
import interactor.repositories.MapRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * [ViewModel] for map screen
 *
 */
class MapViewModel(private val mapRepository: MapRepository) : ViewModel() {
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _storeLoadingResult.value =
                        StoreLoadingResult(
                            response.map { it.convertToKotlinClass() },
                            isLoading = false
                        )
                }, {
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