package interactor.repositories

import interactor.IntroFlagStorage

/**
 * Storage for working with intro flag
 */
class IntroFlagRepository(private val introStatus: IntroFlagStorage) {
    /**
     * Returns true if intro was showed
     *
     */
    fun isIntroShowed() = introStatus.isIntroShowed()

    /**
     * Change value of [isIntroShowed] to true
     *
     */
    fun showIntro() = introStatus.showIntro()
}