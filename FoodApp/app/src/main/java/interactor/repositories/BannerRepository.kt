package interactor.repositories

import com.google.firebase.storage.FirebaseStorage
import domain.Banner
import interactor.utils.FIREBASE_STORAGE_DOWNLOAD_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.Result
import javax.inject.Inject

/**
 * Repository for working with banned images
 *
 */
class BannerRepository @Inject constructor() {

    private val firebaseStorage = FirebaseStorage.getInstance()

    /**
     * List with location of images in firebase storage
     */
    private val firebaseImageLocations = listOf(
        "Doge.jpg",
        "jjl_color_v11_020.jpg",
        "jjl_color_v09_059.jpg"
    )

    /**
     *  Returns a [Result] with [List] of [Banner]
     *
     *  If one of images can not be downloaded returns error
     *
     */
    suspend fun getBanners(): Result<MutableList<Banner>> =
        withContext(Dispatchers.IO) {
            var hasError = false
            val bannerList: MutableList<Banner> = mutableListOf()

            firebaseImageLocations.forEachIndexed { index, imageLocation ->
                firebaseStorage.reference.child(imageLocation).downloadUrl.addOnSuccessListener {
                        bannerList.add(Banner(id = index, imageUrl = it.toString()))
                    }
                    .addOnFailureListener {
                        hasError = true
                    }
            }

            while (bannerList.size != firebaseImageLocations.size || hasError
            ) {
                //waiting until images will be loaded or error
            }

            return@withContext when (hasError) {
                false -> Result.Success(bannerList)
                true -> Result.Error(FIREBASE_STORAGE_DOWNLOAD_ERROR)
            }
        }

}