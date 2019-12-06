package screen.main.rview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import domain.Product
import kotlinx.android.synthetic.main.carousel_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import screen.main.view.ViewpagerItem

/**
 *  An Adapter for [RecyclerView] that shows list of products
 *
 */
class FoodListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class MainTabRvType {
        VIEWPAGER, PRODUCT
    }

    var productList: List<Product> = listOf()
        private set

    var isGrid = false

    private var carouselCheckedItem: Int = 0

    lateinit var buyButtonListener: (context: Context, id: String) -> Toast

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
            productPriceView.text = "${data.id}"
            Glide.with(productImageView.context).load(data.imageId).into(productImageView)
            buyImageButton.setOnClickListener {
                buyButtonListener(it.context, "${data.id}")
            }
        }
    }

    inner class CarouselHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view = v
        fun bind(item: Int) {
            view.carousel_element_tab_pager.addOnPageChangeListener(object :
                ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    carouselCheckedItem = position
                }
            })
            (view as ViewpagerItem).setItem(item)
        }
    }
}