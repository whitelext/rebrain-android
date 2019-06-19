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
open class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.tag("lifecycleFragment").i("onCreateView was called from ${activity?.localClassName}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("lifecycleFragment").i("onCreate was called from ${activity?.localClassName}")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag("lifecycleFragment").i("onAttach was called from ${activity?.localClassName}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.tag("lifecycleFragment").i("onActivityCreated was called from ${activity?.localClassName}")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("lifecycleFragment").i("onStart was called from ${activity?.localClassName}")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("lifecycleFragment").i("onResume was called from ${activity?.localClassName}")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag("lifecycleFragment").i("onPause was called from ${activity?.localClassName}")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag("lifecycleFragment").i("onStop was called from ${activity?.localClassName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag("lifecycleFragment").i("onDestroyView was called from ${activity?.localClassName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag("lifecycleFragment").i("onDestroy was called from ${activity?.localClassName}")
    }
    override fun onDetach() {
        super.onDetach()
        Timber.tag("lifecycleFragment").i("onDetach was called from ${activity?.localClassName}")
    }
}