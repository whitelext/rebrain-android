package interactor.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import service.response.ServerResponse
import utils.Result

/**
 * Root class for repositories that work with network
 *
 */
abstract class BaseNetworkRepository {
    /**
     * Maps Retrofit [Call]<T> into [Result]<T>-entity
     *
     * Passes in [Dispatchers.IO]
     *
     * @param callCreator - request creator. Wrapped in try-catch for exception handling
     */
    protected suspend fun <T : Any> mapCallToResult(
        callCreator: suspend () -> Call<T>
    ): Result<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = callCreator().execute()
                val body = response.body()
                val code = response.code()
                when {
                    body != null -> Result.Success(body)
                    code == 413 -> Result.Error(REQUEST_TOO_LARGE)
                    else -> Result.Error(COMMON_ERROR)
                }
            } catch (e: Exception) {
                Result.Error(e.toString())
            }
        }

    /**
     * Converts server layer [Result]<[ServerResponse]<[T]>>
     * into domain layer [Result]<[T]>
     */
    protected fun <T : Any, R : ServerResponse<T>> Result<R>.convert(): Result<T> =
        when (this) {
            is Result.Success -> Result.Success(data.convertToKotlinClass())
            is Result.Error -> Result.Error(exception)
        }

    /**
     * Converts server layer [Result] <[List]<[ServerResponse]<[T]>>>
     * into domain layer [Result]<[List]<[T]>>
     */
    protected fun <T : Any, R : List<ServerResponse<T>>> Result<R>.convertList(): Result<List<T>> =
        when (this) {
            is Result.Success -> {
                val resultList = mutableListOf<T>()
                data.forEach {
                    resultList.add(it.convertToKotlinClass())
                }
                Result.Success(resultList)
            }
            is Result.Error -> Result.Error(exception)
        }

}