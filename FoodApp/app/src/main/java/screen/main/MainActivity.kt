package screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.whitelext.foodapp.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import screen.main.view.CustomBottomBar.TabType
import service.TestService
import timber.log.Timber
import utils.BaseActivity
import utils.BaseFragment
import utils.ExitDialogFragment
import java.util.concurrent.TimeUnit

/**
 *The Main Screen of application
 *
 * For now it shows [MainFragment] with list of jpg images in a ViewPager
 */
class MainActivity : BaseActivity() {

    private val fragmentTypeMap by lazy {
        hashMapOf(
            TabType.MAIN to MainFragment.TAG,
            TabType.FAVORITES to FavouriteFragment.TAG,
            TabType.PROFILE to ProfileFragment.TAG
        )
    }

    private var mainCompositeDisposable = CompositeDisposable()

    private val changeTitleSubject: PublishSubject<Unit> = PublishSubject.create()

    private val intSubject: PublishSubject<Int> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showFragment(TabType.PROFILE)
        }
        setCheckedButton()

        TestService.stopActionTest(this)

        val changeTitleFiltered = changeTitleSubject
            .delay(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .skip(4)

        changeTitleFiltered.subscribe {
            custom_toolbar.title = "test "
        }

        val intFiltered = intSubject
            .scan { t, u -> t + u }
            .observeOn(AndroidSchedulers.mainThread())

        intFiltered.subscribe {
            Timber.tag("rxTest").i("Current value is $it")
        }

        val booleanObservable = Observable.combineLatest(
            intFiltered,
            changeTitleFiltered,
            BiFunction<Int, Unit, Boolean> { t1, _ -> t1 > 5 })
            .observeOn(AndroidSchedulers.mainThread())

        booleanObservable.subscribe {
            Timber.tag("rxTest").i("Current boolean value is $it")
        }

        intSubject.onNext(1)
        intSubject.onNext(2)
        intSubject.onNext(3)

        initToolbar()
    }

    private fun showFragment(fragmentType: TabType) {
        val fragments = supportFragmentManager.fragments
        fragments.apply {
            forEach {
                if (it is BaseFragment && (it.getFragmentTag() == MainFragment.TAG
                            || it.getFragmentTag() == FavouriteFragment.TAG
                            || it.getFragmentTag() == ProfileFragment.TAG)
                ) {
                    supportFragmentManager.beginTransaction().detach(it).commit()
                }
            }
        }
        val fragment = getFragment(fragmentType)
        val isFragmentAdded =
            supportFragmentManager.findFragmentByTag(fragment.getFragmentTag()) != null
        supportFragmentManager.beginTransaction().apply {
            if (!isFragmentAdded) {
                add(R.id.container, fragment, fragment.getFragmentTag())
            } else {
                attach(fragment)
            }

        }.commit()
    }

    /**
     * Returns fragment of required type from fragment manager or creates a new one if it does not exist
     *
     */
    private fun getFragment(type: TabType): BaseFragment {
        val fragment =
            supportFragmentManager.findFragmentByTag(fragmentTypeMap[type]) as BaseFragment?
        return fragment ?: when (type) {
            TabType.PROFILE -> ProfileFragment.newInstance()
            TabType.MAIN -> MainFragment.newInstance()
            TabType.FAVORITES -> FavouriteFragment.newInstance()

        }

    }

    /**
     * Sets a valid button checked after configuration change
     *
     */
    private fun setCheckedButton() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        currentFragment?.let {
            when ((it as BaseFragment).getFragmentTag()) {
                ProfileFragment.TAG -> main_activity_custom_bottom_bar.updateChecks(TabType.PROFILE)
                MainFragment.TAG -> main_activity_custom_bottom_bar.updateChecks(TabType.MAIN)
                FavouriteFragment.TAG -> main_activity_custom_bottom_bar.updateChecks(TabType.FAVORITES)

            }
        }
    }

    override fun onResume() {
        mainCompositeDisposable.add(main_activity_custom_bottom_bar.setOnTabClickListener(TabType.PROFILE) {
            showFragment(it)
        })

        mainCompositeDisposable.add(main_activity_custom_bottom_bar.setOnTabClickListener(TabType.MAIN) {
            showFragment(it)
        })

        mainCompositeDisposable.add(main_activity_custom_bottom_bar.setOnTabClickListener(TabType.FAVORITES) {
            showFragment(it)
        })

        repeat(5) {
            changeTitle()
        }
        super.onResume()
    }

    override fun onDestroy() {
        TestService.startActionTest(this)
        mainCompositeDisposable.dispose()
        super.onDestroy()
    }

    private fun initToolbar() {
        custom_toolbar.title = getString(R.string.app_name)
        setSupportActionBar(custom_toolbar)
    }

    private fun changeTitle() {
        changeTitleSubject.onNext(Unit)
    }

    override fun onBackPressed() {
        safeExit()
    }

    private fun safeExit() {
        ExitDialogFragment().show(supportFragmentManager, "exit")
    }

    companion object {
        fun start(context: Context) {
            startActivity(context, Intent(context, MainActivity::class.java), null)
        }
    }
}
