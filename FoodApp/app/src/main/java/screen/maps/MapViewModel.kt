package screen.maps

import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.whitelext.foodapp.R
import domain.PickupPoint
import interactor.repositories.MapRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import utils.BaseViewModel
import java.io.IOException

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

    /**
     * Returns address of point on map
     *
     */
    fun getAddress(latLng: LatLng, geoCoder: Geocoder): String {
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            addresses?.let {
                if (addresses.isNotEmpty()) {
                    address = addresses[0]
                    for (i in 0..address.maxAddressLineIndex) {
                        addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(
                            i
                        )
                    }
                }
            }
        } catch (e: IOException) {
            Timber.e(e.localizedMessage)
        }

        return addressText
    }

    /**
     * Returns distance between two points on map
     *
     */
    fun getDistance(LatLng1: LatLng, LatLng2: LatLng): Float {
        var distance = 0.0f
        val locationA = Location("A")
        locationA.latitude = LatLng1.latitude
        locationA.longitude = LatLng1.longitude
        val locationB = Location("B")
        locationB.latitude = LatLng2.latitude
        locationB.longitude = LatLng2.longitude
        distance = locationA.distanceTo(locationB)
        return distance
    }

    /**
     * Returns address and distance from user of closest pickup point
     *
     */
    fun getClosestPickupPointFormattedInfo(
        pickupPointMap: Map<LatLng, PickupPoint>,
        lastLocation: Location,
        geoCoder: Geocoder
    ): Pair<String, Float> {
        val closestPoint = pickupPointMap.toList().sortedBy { (key, _) ->
            getDistance(
                key,
                LatLng(lastLocation.latitude, lastLocation.longitude)
            )
        }.first()
        val closestPointLocation = closestPoint.first
        val userLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
        val closestPointAddress = getAddress(closestPointLocation, geoCoder)
        val closestPointDistance = getDistance(closestPointLocation, userLocation)

        return Pair(closestPointAddress, closestPointDistance)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}