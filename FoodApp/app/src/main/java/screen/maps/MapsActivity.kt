package screen.maps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerMapComponent
import di.MapActivityModule
import domain.PickupPoint
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.layout_toolbar_map.*
import kotlinx.android.synthetic.main.map_bottom_sheet.*
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    @Inject
    lateinit var viewmodel: MapViewModel

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private val pickupPointMap = mutableMapOf<LatLng, PickupPoint>()


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

        setUpMap()
        initViewModel()

        map_zoom_in.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }

        map_zoom_out.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }

        map_user_location.setOnClickListener {

            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        lastLocation.latitude,
                        lastLocation.longitude
                    ), 12f
                )
            )
        }
    }

    private fun setUpMap() {
        val locationPermission = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        //draws dot on user location
        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location
            location?.let {
                lastLocation = location
            }

        }

    }


    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
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


    private fun placeMarkerOnMap(pickupPoint: PickupPoint) {
        val location = LatLng(pickupPoint.location.lat, pickupPoint.location.long)
        val titleStr = getAddress(location)
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
                getDistance(LatLng(lastLocation.latitude, lastLocation.longitude), markerLocation)
            pickup_adress.text = getAddress(markerLocation)
            pickup_title.text = pickupPointMap[markerLocation]?.name
            pickup_work_time.text = pickupPointMap[markerLocation]?.workingHours
            pickup_distance.text = ("$distance м")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            true
        }
    }

    private fun getDistance(LatLng1: LatLng, LatLng2: LatLng): Float {
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

    override fun onMapClick(p0: LatLng?) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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
