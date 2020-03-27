package interactor.repositories

import domain.PickupPoint
import interactor.utils.BaseNetworkRepository
import network.products.ProductsApi
import utils.Result
import javax.inject.Inject

/**
 * Repository for working with data for map
 *
 */
class MapRepository @Inject constructor(
    private val authorizationTokenRepository: AuthorizationTokenRepository,
    private val productsApi: ProductsApi
) : BaseNetworkRepository() {

    /**
     *  Returns a [Result] with [List] of [PickupPoint]
     *
     *
     */
    suspend fun getStoreLocations() =
        mapCallToResult { productsApi.getPickups(authorizationTokenRepository.getAuthroizationToken()) }
            .convertList()

}