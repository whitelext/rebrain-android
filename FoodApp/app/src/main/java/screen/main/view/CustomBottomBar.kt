package screen.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.foodapp.R
import kotlinx.android.synthetic.main.layout_custom_bottom_bar.view.*

/*
    Custom view to work with [CustomBottombarButton]
 */
class CustomBottomBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): LinearLayout(context, attrs){

    enum class TabType{
        MAIN,
        FAVORITES
    }

    private val tabTypeMap by lazy{
        hashMapOf<TabType,CustomBottombarButton>(
            TabType.MAIN to custom_bottom_bar_main_button,
            TabType.FAVORITES to custom_bottom_bar_favorite_button
        )
    }

    init {
        inflate(context, R.layout.layout_custom_bottom_bar, this)
        updateChecks(TabType.MAIN)
    }

     fun setOnTabClickListener(tabType:TabType, listener: (TabType) -> Unit) {
        tabTypeMap[tabType]?.setOnClickListener{
            if(tabTypeMap[tabType]?.isChecked() == false){
                updateChecks(tabType)
                listener(tabType)
            }
        }
    }

    private fun updateChecks(tab:TabType = TabType.MAIN){
        checkTab(tab)
        uncheckOtherTabs(tab)
    }

    private fun checkTab(tab :TabType){
        tabTypeMap[tab]?.setCheck(true)
        tabTypeMap[tab]?.changeState()
    }

    private fun uncheckOtherTabs(tab : TabType){
        tabTypeMap.keys
            .filter{it!=tab}
            .forEach{
                tabTypeMap[it]?.setCheck(false)
                tabTypeMap[it]?.changeState()
            }
    }
}





