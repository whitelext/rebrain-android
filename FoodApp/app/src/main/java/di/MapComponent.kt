package di

import dagger.Component
import di.network.api.ProductsApiModule
import screen.maps.MapViewModel
import screen.maps.MapsActivity

/**
 * Component for [MapsActivity]
 *
 */
@PerScreen
@Component(
    dependencies = [AppComponent::class],
    modules = [MapActivityModule::class,
        ProductsApiModule::class]
)
interface MapComponent {
    fun mapViewModel(): MapViewModel
    fun inject(mapsActivity: MapsActivity)
}