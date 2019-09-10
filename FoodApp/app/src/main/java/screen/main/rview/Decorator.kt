package screen.main.rview

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        val marginDp = margin.dp
        with(outRect) {
            if (parent.getChildAdapterPosition(view) < 2) {
                top = marginDp
            }
            if (parent.getChildAdapterPosition(view) %2 == 0) {
                left = marginDp
            }
            right = marginDp
            bottom = marginDp
        }
    }
}

private val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()