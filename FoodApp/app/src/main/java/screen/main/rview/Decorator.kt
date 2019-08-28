package screen.main.rview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceTopHeight: Int, private val spaceBottomHeight: Int, private val spaceLeftHeight: Int,
                           private val spaceRightHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
        if (parent.getChildAdapterPosition(view) == 0) {
            top = spaceTopHeight
        }
        left =  spaceLeftHeight
        right = spaceRightHeight
        bottom = spaceBottomHeight
    }
    }
}