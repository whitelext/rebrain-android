package interactor.repositories

import com.google.firebase.storage.FirebaseStorage
import domain.Banner
import interactor.utils.COMMON_ERROR
import okhttp3.internal.toImmutableList
import utils.Result
import javax.inject.Inject

/**
 * Repository for working with banned images
 *
 */
class BannerRepository @Inject constructor() {
    private val firebaseStorage = FirebaseStorage.getInstance()


    /**
     *  Returns a [Result] with [List] of [Banner]
     *
     *  If one of images can not be downloaded returns error
     *
     */
    fun getBanners(firebaseImageLocations: List<String>): Result<List<Banner>> {
        var bannerResult: Result<List<Banner>> = Result.Error(COMMON_ERROR)
        val bannerList: MutableList<Banner> = mutableListOf()
        run loop@{
            firebaseImageLocations.forEachIndexed { index, imageLocation ->
                firebaseStorage.reference.child(imageLocation).downloadUrl.addOnSuccessListener {
                        bannerList.add(Banner(id = index, imageUrl = it.toString()))
                        bannerResult = Result.Success(bannerList.toImmutableList())
                    }
                    .addOnFailureListener {
                        bannerResult = Result.Error("loading error")
                    }
                if (bannerResult == Result.Error("loading error"))
                    return@loop
            }
        }
        return bannerResult
    }

}