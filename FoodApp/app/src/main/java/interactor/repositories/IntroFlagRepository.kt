package interactor.repositories

import interactor.IntroFlagStorage
import javax.inject.Inject


/**
 * Storage for working with intro flag
 */
class IntroFlagRepository @Inject constructor(private var introStatus: IntroFlagStorage) {
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