package interactor.repositories

import com.google.firebase.storage.FirebaseStorage
import domain.Banner
import interactor.utils.COMMON_ERROR
import utils.Result

/**
 * Repository for working with banned images
 *
 */
class BannerRepository {
    private val firebaseStorageRef = FirebaseStorage.getInstance().reference
    private var bannerResult: Result<Banner> = Result.Error(COMMON_ERROR)

    /**
     *  Returns a [Result] with [Banner]
     *
     */
    fun getBanner(firebaseImageLocation: String): Result<Banner> {

        firebaseStorageRef.child(firebaseImageLocation).downloadUrl.addOnSuccessListener {
            bannerResult = Result.Success(Banner(id = 0, imageUrl = it.toString()))
        }

        return bannerResult
    }

}