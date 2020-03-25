package screen.main

/**
 * Image loading result : success (image file path) or error message.
 */
data class ImageLoadingResult(
    val success: String? = null,
    val isLoading: Boolean = true,
    val error: Int? = null
)
