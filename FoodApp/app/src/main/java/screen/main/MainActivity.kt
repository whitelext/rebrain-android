package screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.core.content.ContextCompat.startActivity
import com.whitelext.foodapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import screen.main.view.CustomBottomBar.TabType
import utils.BaseActivity
import utils.BaseFragment
import utils.ExitDialogFragment

/**
 *The Main Screen of application
 *
 * For now it shows [MainFragment] with list of jpg images in a ViewPager
 */
class MainActivity : BaseActivity() {

    private val h = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            custom_toolbar.title = "test"
        }
    }

    private val lambda = { custom_toolbar.title = "test" }

    private val fragmentTypeMap by lazy {
        hashMapOf(
            TabType.MAIN to MainFragment.TAG,
            TabType.FAVORITES to FavouriteFragment.TAG,
            TabType.PROFILE to ProfileFragment.TAG
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showFragment(TabType.PROFILE)
        }
        setCheckedButton()

        main_activity_custom_bottom_bar.setOnTabClickListener(TabType.PROFILE) {
            showFragment(it)
        }
        main_activity_custom_bottom_bar.setOnTabClickListener(TabType.MAIN) {
            showFragment(it)
        }

        main_activity_custom_bottom_bar.setOnTabClickListener(TabType.FAVORITES) {
            showFragment(it)
        }


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
        super.onResume()
        changeTitle()
    }

    private fun initToolbar() {
        custom_toolbar.title = getString(R.string.app_name)
        setSupportActionBar(custom_toolbar)
    }

    private fun changeTitle() {
        Thread(Runnable {
            h.postDelayed(lambda, 1000)
        }).start()
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
