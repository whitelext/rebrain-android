package screen.intro

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import com.example.foodapp.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }
    companion object{
        fun start(context: Context){
            startActivity(context, Intent(context,IntroActivity::class.java),null)
        }
    }
}
