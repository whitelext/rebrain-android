package screen.main.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.foodapp.R
import kotlinx.android.synthetic.main.layout_custom_bottom_bar.view.*
import kotlinx.android.synthetic.main.layout_custom_bottom_button.view.*
import screen.main.FavouriteFragment
import screen.main.MainActivity
import screen.main.MainFragment
/*
    Custom view to work with [CustomBottombarButton]
 */
class CustomBottomBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs),View.OnClickListener{

    private var isFavoriteChecked = false

    init {
        inflate(context, R.layout.layout_custom_bottom_bar, this)
        setMainChecked()
        custom_bottom_bar_favorite_button.setOnClickListener(this)
        custom_bottom_bar_main_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.custom_bottom_bar_main_button  -> {
                if(isFavoriteChecked) { // Needed to not run useless code when click happens
                    setMainChecked()
                    showFragment(MainFragment.newInstance())
                    isFavoriteChecked = false
                }
            }
            R.id.custom_bottom_bar_favorite_button  -> {
                if (!isFavoriteChecked) {
                    setFavoriteChecked()
                    showFragment(FavouriteFragment.newInstance())
                    isFavoriteChecked = true
                }
            }
        }
    }

     private fun setMainChecked(){
         custom_bottom_bar_main_button.custom_button_text.setTextAppearance(context,R.style.Text_12sp_Green)
         custom_bottom_bar_main_button.custom_button_image.setImageResource(R.drawable.img_main_bottom)
         custom_bottom_bar_favorite_button.custom_button_text.setTextAppearance(context,R.style.Text_12sp_Gray)
         custom_bottom_bar_favorite_button.custom_button_image.setImageResource(R.drawable.img_favourites_bottom)
         isFavoriteChecked = false
    }

    private fun setFavoriteChecked(){
        custom_bottom_bar_main_button.custom_button_text.setTextAppearance(context,R.style.Text_12sp_Gray)
        custom_bottom_bar_main_button.custom_button_image.setImageResource(R.drawable.img_main_unchecked)
        custom_bottom_bar_favorite_button.custom_button_text.setTextAppearance(context,R.style.Text_12sp_Green)
        custom_bottom_bar_favorite_button.custom_button_image.setImageResource(R.drawable.img_favorite_checked)
        isFavoriteChecked = true
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment).commit()
    }

}




