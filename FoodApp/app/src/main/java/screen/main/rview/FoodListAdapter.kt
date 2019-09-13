package screen.main.rview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.google.android.material.tabs.TabLayout
import domain.Product
import kotlinx.android.synthetic.main.carousel_item.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import screen.main.carousel.adapter.CarouselStatePageAdapter

/**
 *  An Adapter for [RecyclerView] that shows list of products
 *
 */
class FoodListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class MainTabRvType{
        VIEWPAGER, PRODUCT
    }

    var productList: MutableList<Product> = mutableListOf()
        private set

    var isGrid = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == MainTabRvType.VIEWPAGER.ordinal){
            val layoutRv = R.layout.carousel_item
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

    fun setProductList(list: MutableList<Product>) {
        productList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position==0)
            MainTabRvType.VIEWPAGER.ordinal
        else MainTabRvType.PRODUCT.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            MainTabRvType.VIEWPAGER.ordinal -> {
                (holder as CarouselHolder).bind()
            }
            else ->{
                (holder as ProductHolder).bind(productList[position])
            }
        }
    }

    class ProductHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val productNameView: TextView = v.card_main_element_text
        private val productPriceView: TextView = v.card_main_element_price
        private val productImageView: ImageView = v.card_main_element_image

        fun bind(data: Product) {
            productNameView.text = data.name
            productPriceView.text = "${data.id}"
            Glide.with(productImageView.context).load(data.imageId).into(productImageView)
        }
    }

    class CarouselHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val viewPager: ViewPager = v.carousel_element_tab_pager
        private val tabLayout: TabLayout = v.carousel_element_tab_layout
        private val fm: FragmentManager = (v.context as FragmentActivity).supportFragmentManager
        private val pictures = listOf(
            R.drawable.img_carousel_1,
            R.drawable.img_carousel_2,
            R.drawable.img_carousel_3,
            R.drawable.img_carousel_4,
            R.drawable.img_carousel_5,
            R.drawable.img_carousel_6,
            R.drawable.img_carousel_7,
            R.drawable.img_carousel_8,
            R.drawable.img_carousel_9,
            R.drawable.img_carousel_10
        )

        fun bind(){
            viewPager.adapter = CarouselStatePageAdapter(fm,pictures)
            tabLayout.setupWithViewPager(viewPager,true)
        }
    }
}