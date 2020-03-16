package screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerProfileFragmentComponent
import di.ProfileFragmentModule
import kotlinx.android.synthetic.main.profile_fragment.*
import screen.main.viewmodel.ProfileViewModel
import utils.BaseFragment
import javax.inject.Inject

/**
 * Fragment for showing profile screen
 *
 */
class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component =
            DaggerProfileFragmentComponent.builder()
                .appComponent(((this.requireActivity().application) as FoodApplication).getAppComponent())
                .profileFragmentModule(ProfileFragmentModule(this))
                .build()
        component.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    fun initViewModel(){
        viewModel.loggedUserName.observe(viewLifecycleOwner, Observer {
            profileName.text = it
        })
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    companion object {
        fun newInstance() = ProfileFragment()
        const val TAG = "ProfileFragment"
    }

}
