package interactor.repositories

import domain.PickupPoint
import interactor.utils.BaseNetworkRepository
import io.reactivex.Single
import network.products.ProductsApi
import service.response.PickPointResponse
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
    fun getStoreLocations(): Single<List<PickPointResponse>> {
        return productsApi.getPickups(authorizationTokenRepository.getAuthroizationToken())
    }
}