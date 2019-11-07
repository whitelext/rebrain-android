package interactor.repositories

import utils.Storage

/**
 * Storage for working with intro flag
 */
class IntroFlagRepository(private val introStatus: Storage) {
    /**
     * Returns true if intro was showed
     *
     */
    fun isIntroShowed() = introStatus.getElement()

    /**
     * Change value of [isIntroShowed] to true
     *
     */
    fun showIntro() = introStatus.saveElement(true)
}