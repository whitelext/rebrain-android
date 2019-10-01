package screen.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodapp.R
import utils.BaseFragment
import utils.Logger

/**
 * A [Fragment] subclass for showing favourite food
 *
 */
class FavouriteFragment : BaseFragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    companion object {
        fun newInstance(): FavouriteFragment {
            return FavouriteFragment()
        }
    }
}
