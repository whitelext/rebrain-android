package screen.maps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerMapComponent
import di.MapActivityModule
import domain.PickupPoint
import kotlinx.android.synthetic.main.layout_toolbar_map.*
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Inject
    lateinit var viewmodel: MapViewModel

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location


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
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    private fun initViewModel() {
        viewmodel.loadStores()

        viewmodel.storeLoadingResult.observe(this, Observer {loadingResult->

            loadingResult.success?.let{
                it.forEach { pickupPoint ->
                    placeMarkerOnMap(pickupPoint)
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()
        initViewModel()
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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
                val currentLatLng = LatLng(location.latitude, location.longitude)
                // placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
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
