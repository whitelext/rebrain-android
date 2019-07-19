package screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import utils.BaseActivity
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_custom_bottom_bar.*
import kotlinx.android.synthetic.main.layout_custom_bottom_bar.view.*
import screen.main.view.CustomBottomBar
import screen.main.view.CustomBottomBar.TabType
import utils.BaseFragment

/**
 *The Main Screen of application
 *
 * For now it shows [MainFragment] with list of jpg images in a ViewPager
 */
class MainActivity : BaseActivity() {

    private val FragmentTypeMap by lazy{
        hashMapOf<TabType, BaseFragment>(
            TabType.MAIN to MainFragment.newInstance(),
            TabType.FAVORITES to FavouriteFragment.newInstance()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(TabType.MAIN)

        main_activity_custom_bottom_bar.setOnTabClickListener(TabType.MAIN){
            showFragment(it)
        }

        main_activity_custom_bottom_bar.setOnTabClickListener(TabType.FAVORITES){
            showFragment(it)
        }
    }

    private fun showFragment(fragmentType: TabType) {
        val fragments = supportFragmentManager.fragments
        fragments.apply {
            if(isNotEmpty())
                forEach {
                    supportFragmentManager.beginTransaction().detach(it).commit()
                }
        }
        val fragment = FragmentTypeMap[fragmentType]
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentById(fragment!!.id) == null){
            fragmentTransaction.add(R.id.container,fragment)
        } else{
            fragmentTransaction.attach(fragment)
        }
        fragmentTransaction.commit()
    }

    companion object{
        fun start(context: Context){
            startActivity(context,Intent(context, MainActivity::class.java),null)
        }
    }
}
