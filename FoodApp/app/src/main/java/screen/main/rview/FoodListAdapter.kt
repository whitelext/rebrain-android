package screen.main.rview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import domain.Product
import kotlinx.android.synthetic.main.list_item.view.*

/**
 *  An Adapter for [RecyclerView] that shows list of products
 *
 */
class FoodListAdapter : RecyclerView.Adapter<FoodListAdapter.Holder>() {

    var productList: MutableList<Product> = mutableListOf()
        private set

    var isGrid = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                if (isGrid) {
                    R.layout.list_item_grid
                } else R.layout.list_item,
                parent,
                false
            )
        )
    }

    fun setProductList(list: MutableList<Product>) {
        productList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(productList[position])
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val productNameView: TextView = v.card_main_element_text
        val productPriceView: TextView = v.card_main_element_price
        val productImageView: ImageView = v.card_main_element_image

        fun bind(data: Product) {
            productNameView.text = data.name
            productPriceView.text = "${data.id}"
            Glide.with(productImageView.context).load(data.imageId).into(productImageView)
        }
    }
}