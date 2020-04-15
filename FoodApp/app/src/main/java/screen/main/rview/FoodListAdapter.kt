package screen.main.rview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.whitelext.foodapp.R
import domain.Banner
import domain.Product
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.list_item.view.*
import screen.main.view.ViewpagerItem

/**
 *  An Adapter for [RecyclerView] that shows list of products
 *
 */
class FoodListAdapter(
    var buyButtonListener: (context: Context, id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class MainTabRvType {
        VIEWPAGER, PRODUCT
    }

    var productList: List<Product> = listOf()
        private set

    var isGrid = false

    var carouselCheckedItem: Int = 0

    var bannerList: MutableList<Banner> = mutableListOf()
        private set

    var productItemCompositeDisposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MainTabRvType.VIEWPAGER.ordinal) {
            val layoutRv = R.layout.viewpager_item
            return CarouselHolder(
                LayoutInflater.from(parent.context).inflate(
                    layoutRv,
                    parent,
                    false
                )
            )
        } else {
            val layoutRv = if (isGrid)
                R.layout.list_item_grid
            else
                R.layout.list_item
            return ProductHolder(
                LayoutInflater.from(parent.context).inflate(
                    layoutRv,
                    parent,
                    false
                )
            )
        }
    }

    fun setProductList(list: List<Product>) {
        productList = list
        notifyDataSetChanged()
    }

    fun setBannerList(list: MutableList<Banner>) {
        bannerList = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return productList.size + 1
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            MainTabRvType.VIEWPAGER.ordinal
        else MainTabRvType.PRODUCT.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            MainTabRvType.VIEWPAGER.ordinal -> {
                (holder as CarouselHolder).bind(carouselCheckedItem)
            }
            else -> {
                (holder as ProductHolder).bind(productList[position - 1])
            }
        }
    }

    inner class ProductHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val productNameView: TextView = v.card_main_element_text
        private val productPriceView: TextView = v.card_main_element_price
        private val productImageView: ImageView = v.card_main_element_image
        private val buyImageButton: ImageButton = v.card_main_element_buy_button

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
            productItemCompositeDisposable.add(buyImageButton.clicks().subscribe {
                buyButtonListener(buyImageButton.context, data.id)
            })
        }
    }

    inner class CarouselHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view = v as ViewpagerItem
        fun bind(item: Int) {
            view.setupListener(this@FoodListAdapter)
            view.setBannerImages(bannerList)
            view.setItem(item)
        }
    }
}