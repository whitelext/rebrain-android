package screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import utils.BaseActivity
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_custom_bottom_bar.*
import kotlinx.android.synthetic.main.layout_custom_bottom_bar.view.*

/**
 *The Main Screen of application
 *
 * For now it shows [MainFragment] with list of jpg images in a ViewPager
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        custom_bottom_bar_main_button.setOnClickListener {
            val toast = Toast.makeText(
                applicationContext,
                "just test", Toast.LENGTH_SHORT
            )
            toast.show()
        }
        showFragment(MainFragment.newInstance())

    }

    fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_activity, fragment).commit()
    }

    companion object{
        fun start(context: Context){
            startActivity(context,Intent(context, MainActivity::class.java),null)
        }

    }
}
