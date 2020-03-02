package interactor.repositories

import com.whitelext.foodapp.R
import domain.Product
import network.products.ProductsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.response.PickPointsResponse
import timber.log.Timber
import utils.Generator
import javax.inject.Inject

/**
 * A class that manage data from data sources
 */
class ProductsRepository @Inject constructor(
    private val foodGenerator: Generator,
    private val productsApi: ProductsApi
) {
    /**
     * @return [List] of [Product]
     */
    fun getProductList(): List<Product> {
        return foodGenerator.getProducts()
    }

    /**
     * @return [List] of pictures
     */
    fun getCarouselPictures(): List<Int> {
        return listOf(
            R.drawable.img_carousel_1,
            R.drawable.img_carousel_2,
            R.drawable.img_carousel_3,
            R.drawable.img_carousel_4,
            R.drawable.img_carousel_5,
            R.drawable.img_carousel_6,
            R.drawable.img_carousel_7,
            R.drawable.img_carousel_8,
            R.drawable.img_carousel_9,
            R.drawable.img_carousel_10
        )
    }

    fun callApi() {
        productsApi.getPickups().enqueue(object : Callback<PickPointsResponse> {
            override fun onFailure(call: Call<PickPointsResponse>, t: Throwable) {
                Timber.tag("Network").i("api call failed")
            }

            override fun onResponse(
                call: Call<PickPointsResponse>,
                response: Response<PickPointsResponse>
            ) {
                Timber.tag("Network").i("api call succeed")
            }

        })

    }
}