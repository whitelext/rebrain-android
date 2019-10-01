package screen.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import utils.BaseActivity
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_intro.*
import screen.main.MainActivity
import utils.Logger

/*
** Activity that shows new users what our app can do
*
* A flag that activity was shown and no more needed is stored in Shared Preferences
*
* User will be navigated to Main Screen after taping any part of screen
 */
class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        intro_screen.setOnClickListener {
            MainActivity.start(this)
            finishAffinity()
        }
    }
    companion object{
        fun start(context: Context){
            startActivity(context, Intent(context,IntroActivity::class.java),null)
        }
    }
}
