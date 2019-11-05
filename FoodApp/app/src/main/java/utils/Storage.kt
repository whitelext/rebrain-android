package utils

/**
 * Interface for storages
 *
 */
interface Storage {
    /**
     *Returns value of element from Shared Preferences
     *
     */
    fun getElement(): Boolean

    /**
     * Saving element to Shared Preferences
     *
     */
    fun saveElement(value: Boolean)
}