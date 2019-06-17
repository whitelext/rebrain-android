package screen.main.carousel
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodapp.R
import kotlinx.android.synthetic.main.fragment_carousel.view.*
import timber.log.Timber

private const val IMG_KEY = "num"

/**
 *  [Fragment] that shows image for ViewPager
 */
class CarouselFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag("lifecycleFragment").i("onCreateView was called from $this")
       return  inflater.inflate(R.layout.fragment_carousel, container, false).apply {
           arguments?.getInt(IMG_KEY)?.let { this.carousel_imageView.setImageResource(it) }
       }
    }

    companion object {
        fun newInstance(imgId : Int) =
           CarouselFragment().apply {
                arguments = Bundle().apply {
                    putInt(IMG_KEY, imgId)
                }
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("lifecycleFragment").i("onCreate was called from $this")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag("lifecycleFragment").i("onAttach was called from $this")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.tag("lifecycleFragment").i("onActivityCreated was called from $this")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("lifecycleFragment").i("onStart was called from $this")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("lifecycleFragment").i("onResume was called from $this")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag("lifecycleFragment").i("onPause was called from $this")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag("lifecycleFragment").i("onStop was called from $this")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag("lifecycleFragment").i("onDestroyView was called from $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("lifecycleFragment").i("onDestroy was called from $this")
    }
    override fun onDetach() {
        super.onDetach()
        Timber.tag("lifecycleFragment").i("onDetach was called from $this")
    }
}
