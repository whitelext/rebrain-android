package screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.foodapp.R
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

    private val fragmentTypeMap by lazy {
        hashMapOf(
            TabType.MAIN to MainFragment.TAG,
            TabType.FAVORITES to FavouriteFragment.TAG
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCheckedButton()

        main_activity_custom_bottom_bar.setOnTabClickListener(TabType.MAIN) {
            showFragment(it)
        }

        main_activity_custom_bottom_bar.setOnTabClickListener(TabType.FAVORITES) {
            showFragment(it)
        }
        if (savedInstanceState == null) {
            showFragment(TabType.MAIN)
        }
        initToolbar()
    }

    private fun showFragment(fragmentType: TabType) {
        val fragments = supportFragmentManager.fragments
        fragments.apply {
            forEach {
                if (it is BaseFragment && (it.getFragmentTag() == MainFragment.TAG || it.getFragmentTag() == FavouriteFragment.TAG)) {
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
                MainFragment.TAG -> main_activity_custom_bottom_bar.updateChecks(TabType.MAIN)
                FavouriteFragment.TAG -> main_activity_custom_bottom_bar.updateChecks(TabType.FAVORITES)
            }
        }
    }

    private fun initToolbar() {
        custom_toolbar.title = getString(R.string.app_name)
        setSupportActionBar(custom_toolbar)
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
