package screen.main.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_custom_bottom_button.view.*
import com.example.foodapp.R

/*
    Custom view far working with tabs
 */
class CustomBottombarButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr){

    private var title:String? = null
    private var image:Drawable?= null

    init {
        inflate(context, R.layout.layout_custom_bottom_button, this)
        custom_button_linear_layout.orientation = VERTICAL
        applyAttrs(attrs).recycle()

    }
    enum class Buttons()
    private fun applyAttrs( attrs: AttributeSet?): TypedArray {
        val attributes = context.obtainStyledAttributes(attrs,R.styleable.CustomBottombarButton)
        image = attributes.getDrawable(R.styleable.CustomBottombarButton_image)
        title = attributes.getString(R.styleable.CustomBottombarButton_text)
        return attributes
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        custom_button_text.text = title
        custom_button_image.setImageDrawable(image)
    }
}