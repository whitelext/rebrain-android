package screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import utils.BaseActivity
import com.example.foodapp.R

/**
 *The Main Screen of application
 *
 * For now it shows [MainFragment] with list of jpg images in a ViewPager
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(MainFragment.newInstance())
    }

    private fun showFragment(fragment: MainFragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_activity, fragment).commit()
    }

    companion object{
        fun start(context: Context){
            startActivity(context,Intent(context, MainActivity::class.java),null)
        }
    }
}
