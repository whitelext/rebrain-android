package screen.main

import domain.Banner

/**
 * Banners loading result : success (List of [Banner]) or error message.
 */
data class BannerLoadingResult(
    val success: List<Banner>? = null,
    val isLoading: Boolean = true,
    val error: Int? = null
)