package di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import interactor.repositories.AuthorizationTokenRepository
import interactor.repositories.MapRepository
import network.products.ProductsApi
import screen.maps.MapViewModel
import screen.maps.MapViewModelFactory

/**
 * Provides [MapViewModel] for map screen
 *
 */
@Module
class MapActivityModule(private val activity: FragmentActivity) {

    @Provides
    @PerScreen
    fun provideMapRepository(
        authorizationTokenRepository: AuthorizationTokenRepository,
        productsApi: ProductsApi
    ) = MapRepository(authorizationTokenRepository, productsApi)

    @Provides
    @PerScreen
    fun provideMapViewModelFactory(
        mapRepository: MapRepository
    ): MapViewModelFactory =
        MapViewModelFactory(
            mapRepository
        )

    @Provides
    @PerScreen
    fun provideMapViewModel(mapViewModelFactory: MapViewModelFactory): MapViewModel =
        ViewModelProviders.of(
            activity,
            mapViewModelFactory
        )
            .get(MapViewModel::class.java)
}