package utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Root Fragment class that helps to observe fragment lifecycle avoiding boilerplate code for multiple fragments
 */
abstract class BaseFragment : Fragment() {

    abstract fun getFragmentTag(): String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.tag("lifecycleFragment").i("onCreateView was called from ${activity?.localClassName}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(Logger("${activity?.localClassName} fragment"))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag("lifecycleFragment").i("onAttach was called from ${activity?.localClassName}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.tag("lifecycleFragment").i("onActivityCreated was called from ${activity?.localClassName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag("lifecycleFragment").i("onDestroyView was called from ${activity?.localClassName}")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.tag("lifecycleFragment").i("onDetach was called from ${activity?.localClassName}")
    }
}