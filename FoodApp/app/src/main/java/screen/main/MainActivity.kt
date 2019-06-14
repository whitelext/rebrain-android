package screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import com.example.foodapp.BaseActivity
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 *The Main Screen of application
 *
 * For now it just shows name of it's class
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView_main.text = this.localClassName
    }
    companion object{
        fun start(context: Context){
            startActivity(context,Intent(context, MainActivity::class.java),null)
        }
    }
}
