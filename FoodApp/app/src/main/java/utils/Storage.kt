package utils

/**
 * Interface for storages
 *
 */
interface Storage<T> {
    /**
     *Returns value of element from Shared Preferences
     *
     */
    fun getElement(): T

    /**
     * Saving element to Shared Preferences
     *
     */
    fun saveElement(value: T)
}