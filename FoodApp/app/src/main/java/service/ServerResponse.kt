package service

/**
 * Interface for response classes that deserialize Json to domain objects
 *
 * @param T is type of domain object
 */
interface ServerResponse<out T> {
    fun convertToKotlinClass(): T
}