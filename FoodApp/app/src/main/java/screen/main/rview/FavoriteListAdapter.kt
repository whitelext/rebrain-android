package screen.main.rview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import domain.Product
import kotlinx.android.synthetic.main.favorite_list_item.view.*

/**
 *  An Adapter for [RecyclerView] that shows list of favorite products
 *
 */
class FavoriteListAdapter(
    var favButtonListener: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var favoriteList: MutableList<Product> = mutableListOf()
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutRv = R.layout.favorite_list_item
        return FavoriteProductHolder(
            LayoutInflater.from(parent.context).inflate(
                layoutRv,
                parent,
                false
            )
        )
    }

    fun setFavoritesList(list: MutableList<Product>) {
        favoriteList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FavoriteProductHolder).bind(favoriteList[position])
    }

    inner class FavoriteProductHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val productNameView: TextView = v.card_favorite_element_text
        private val productPriceView: TextView = v.card_favorite_element_price
        private val productImageView: ImageView = v.card_favorite_element_image
        private val favButton: ImageButton = v.card_favorite_button

        fun bind(data: Product) {
            productNameView.text = data.name
            productPriceView.text = "${data.price}"
            Glide.with(productImageView.context).load(
                when (data.id % 4) {
                    0 -> R.drawable.img_list_1
                    1 -> R.drawable.img_list_2
                    2 -> R.drawable.img_list_3
                    else -> R.drawable.img_list_4
                }
            ).into(productImageView)
            favButton.setOnClickListener {
                favButtonListener(data.id)
                notifyDataSetChanged()
            }
        }
    }

}