package screen.main.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foodapp.R
import kotlinx.android.synthetic.main.layout_custom_bottom_bar.view.*
import kotlinx.android.synthetic.main.layout_custom_bottom_button.view.*
import screen.main.FavouriteFragment
import screen.main.MainActivity
import screen.main.MainFragment

class CustomBottomBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs){

    init {
        inflate(context, R.layout.layout_custom_bottom_bar, this)
        setState(custom_bottom_bar_favorite_button,State.Favourite)
        setState(custom_bottom_bar_main_button,State.Main)
    }

    private fun setState(button: CustomBottombarButton, state :State){
        when (state){
            State.Main->{
                button.custom_button_text.setTextAppearance(context,R.style.Text_12sp_Green)
                button.setOnClickListener{
                        // Some onClick behavior
                }

            }
            State.Favourite->{
                button.custom_button_text.setTextAppearance(context,R.style.Text_12sp_Gray)
                button.setOnClickListener {
                   Toast.makeText(context,"kek",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}




