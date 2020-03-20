package utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import service.response.ServerResponse

/**
 * Маппинг Retrofit [Call]<T> в [Result]<T>-сущность
 *
 * Происходит в [Dispatchers.IO]
 *
 * @param callCreator - создатель запросов. Обернут в try-блок для обработки ошибок,
 * и может содержать не только само создание запроса, но и другие действия, которые необходимы
 * для формирования запроса
 */
suspend fun <T : Any> mapCallToResult(
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
                else -> Result.Error("common error")
            }
        } catch (e: Exception) {
            Result.Error(e.toString())
        }
    }

/**
 * Конвертация результата сервисного слоя [Result]<[ServerResponse]<[T]>>
 * в результат доменного слоя [Result]<[T]>
 */
fun <T : Any, R : ServerResponse<T>> Result<R>.convert(): Result<T> =
    when (this) {
        is Result.Success -> Result.Success(data.convertToKotlinClass())
        is Result.Error -> Result.Error(exception)
    }
