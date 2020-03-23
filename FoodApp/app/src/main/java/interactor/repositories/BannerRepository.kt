package interactor.repositories

import com.google.firebase.storage.FirebaseStorage
import domain.Banner
import interactor.utils.COMMON_ERROR
import kotlinx.coroutines.*
import okhttp3.internal.toImmutableList
import utils.Result
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Repository for working with banned images
 *
 */
class BannerRepository @Inject constructor() : CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val firebaseStorage = FirebaseStorage.getInstance()


    /**
     *  Returns a [Result] with [List] of [Banner]
     *
     *  If one of images can not be downloaded returns error
     *
     */
    suspend fun getBanners(firebaseImageLocations: List<String>): Result<List<Banner>> {
        var bannerResult: Result<List<Banner>> = Result.Error(COMMON_ERROR)
        val bannerList: MutableList<Banner> = mutableListOf()
        coroutineScope {
            launch {
                val operation = async(Dispatchers.IO) {
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
                }
                operation.await()
            }
        }
        return bannerResult
    }

}