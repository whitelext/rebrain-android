package screen.maps

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerMapComponent
import di.MapActivityModule
import domain.PickupPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.layout_toolbar_map.*
import kotlinx.android.synthetic.main.map_bottom_sheet.*
import service.MapService
import javax.inject.Inject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    @Inject
    lateinit var viewmodel: MapViewModel

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var geocoder: Geocoder
    private val pickupPointMap = mutableMapOf<LatLng, PickupPoint>()


    private var mapCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val component =
            DaggerMapComponent.builder()
                .appComponent(((this.application) as FoodApplication).getAppComponent())
                .mapActivityModule(MapActivityModule(this))
                .build()
        component.inject(this)
        initToolbar()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        bottomSheetBehavior = BottomSheetBehavior.from(map_bottom_sheet_layout)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        map_fragment.setPadding(0, 0, 0, map_bottom_sheet_layout.height)
                        map_buttons.setPadding(0, 0, 0, map_bottom_sheet_layout.height)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        map_fragment.setPadding(0, 0, 0, 0)
                        map_buttons.setPadding(0, 0, 0, 0)
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        map_fragment.setPadding(0, 0, 0, 0)
                        map_buttons.setPadding(0, 0, 0, 0)
                    }
                }
            }

        })

    }

    override fun onDestroy() {
        mapCompositeDisposable.dispose()
        super.onDestroy()
    }

    private fun initViewModel() {
        viewmodel.loadStores()

        viewmodel.storeLoadingResult.observe(this, Observer { loadingResult ->

            map_progressBar.isVisible = loadingResult.isLoading

            loadingResult.success?.let {
                val latLngBounds = LatLngBounds.builder()
                it.forEach { pickupPoint ->
                    placeMarkerOnMap(pickupPoint)
                    val pointLocation = LatLng(
                        pickupPoint.location.lat,
                        pickupPoint.location.long
                    )
                    pickupPointMap[pointLocation] = pickupPoint
                    latLngBounds.include(pointLocation)
                }
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 100))
                MapService.startMapService(
                    this,
                    viewmodel.getClosestPickupPointFormattedInfo(
                        pickupPointMap,
                        lastLocation,
                        geocoder
                    )
                )
            }

            loadingResult.error?.let {
                showPickupPointsUploadFailed(it)
            }
        })
    }

    private fun showPickupPointsUploadFailed(@StringRes errorString: Int) {
        Snackbar.make(map_fragment, errorString, Snackbar.LENGTH_LONG).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMapToolbarEnabled = false
        map.setOnMapClickListener(this)
        geocoder = Geocoder(this)

        setUpMap()
        initViewModel()

        mapCompositeDisposable.add(map_zoom_in.clicks().subscribe {
            map.animateCamera(CameraUpdateFactory.zoomIn())
        })

        mapCompositeDisposable.add(map_zoom_out.clicks().subscribe {
            map.animateCamera(CameraUpdateFactory.zoomOut())
        })

        mapCompositeDisposable.add(map_user_location.clicks().subscribe {

            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        lastLocation.latitude,
                        lastLocation.longitude
                    ), 12f
                )
            )
        })
    }

    private fun setUpMap() {
        //draws dot on user location
        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location
            location?.let {
                lastLocation = location
            }

        }

    }

    private fun placeMarkerOnMap(pickupPoint: PickupPoint) {
        val location = LatLng(pickupPoint.location.lat, pickupPoint.location.long)
        val titleStr = viewmodel.getAddress(location, geocoder)
        val markerOptions = MarkerOptions().position(location).title(titleStr).icon(
            BitmapDescriptorFactory.fromResource(
                R.drawable.ic_location_pickup
            )
        )
        map.addMarker(markerOptions)
        map.setOnMarkerClickListener { marker ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            val markerLocation = LatLng(marker.position.latitude, marker.position.longitude)
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            val distance =
                viewmodel.getDistance(
                    LatLng(lastLocation.latitude, lastLocation.longitude),
                    markerLocation
                )
            pickup_adress.text = viewmodel.getAddress(markerLocation, geocoder)
            pickup_title.text = pickupPointMap[markerLocation]?.name
            pickup_work_time.text = pickupPointMap[markerLocation]?.workingHours
            pickup_distance.text = ("$distance м")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            true
        }
    }


    override fun onMapClick(p0: LatLng?) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onStop() {
        MapService.stop(this)
        super.onStop()
    }

    private fun initToolbar() {
        custom_toolbar_map.title = getString(R.string.map_toolbar_text)
        setSupportActionBar(custom_toolbar_map)
    }

    companion object {
        fun start(context: Context) {
            ContextCompat.startActivity(context, Intent(context, MapsActivity::class.java), null)
        }

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
