package screen.main.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.whitelext.foodapp.R
import kotlinx.android.synthetic.main.layout_custom_bottom_button.view.*

/*
    Custom view far working with tabs
 */
class CustomBottombarButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var title: String? = null
    private var checkedImage: Drawable? = null
    private var uncheckedImage: Drawable? = null
    private var isChecked = false

    init {
        inflate(context, R.layout.layout_custom_bottom_button, this)
        custom_button_linear_layout.orientation = VERTICAL
        applyAttrs(attrs).recycle()
    }

    private fun applyAttrs(attrs: AttributeSet?): TypedArray {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomBottombarButton)
        checkedImage = attributes.getDrawable(R.styleable.CustomBottombarButton_image_checked)
        uncheckedImage = attributes.getDrawable(R.styleable.CustomBottombarButton_image_unchecked)
        title = attributes.getString(R.styleable.CustomBottombarButton_text)
        isChecked = attributes.getBoolean(R.styleable.CustomBottombarButton_isChecked, false)
        return attributes
    }

    fun changeState() {
        if (isChecked) {
            custom_button_image.setImageDrawable(checkedImage)
            custom_button_text.setTextAppearance(context, R.style.Text_12sp_Green)
        } else {
            custom_button_image.setImageDrawable(uncheckedImage)
            custom_button_text.setTextAppearance(context, R.style.Text_12sp_Gray)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        custom_button_text.text = title
        changeState()
    }

    fun setCheck(state: Boolean) {
        isChecked = state
    }

    fun isChecked(): Boolean {
        return isChecked
    }
}
