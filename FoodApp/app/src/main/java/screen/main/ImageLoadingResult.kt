package screen.main

import android.graphics.Bitmap

/**
 * Image loading result : success (bitmap image]) or error message.
 */
data class ImageLoadingResult(
    val success: Bitmap? = null,
    val isLoading: Boolean = true,
    val error: Int? = null
)
