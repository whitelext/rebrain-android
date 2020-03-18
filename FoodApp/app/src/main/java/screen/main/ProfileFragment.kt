package screen.main

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
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

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Capture photo from camera")
        pictureDialog.setItems(
            pictureDialogItems
        ) { _, which ->
            when (which) {
                0 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA) {
            image_progressBar.visibility = View.VISIBLE
            profileAvatar.visibility = View.GONE
            val thumbnail = (data!!.extras!!.get("data") as Bitmap).rotate(90f)
            viewModel.setUserImage(thumbnail)
        }
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
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
        profileAvatar.setOnClickListener {
            showPictureDialog()
        }
    }

    private fun initViewModel() {
        viewModel.loggedUserName.observe(viewLifecycleOwner, Observer {
            profileName.text = it
        })

        viewModel.imageLoadingResult.observe(viewLifecycleOwner, Observer { loadingResult ->
            if (loadingResult.isLoading) {
                return@Observer
            }

            image_progressBar.visibility = View.GONE
            profileAvatar.visibility = View.VISIBLE

            loadingResult.error?.let {
                showImageUploadFailed(it)
            }

            loadingResult.success?.let {
                updateUserImage(it)
            }
        })

    }

    private fun showImageUploadFailed(@StringRes errorString: Int) {
        Snackbar.make(profileAvatar, errorString, Snackbar.LENGTH_LONG).show()
    }

    private fun updateUserImage(image: Bitmap) {
        profileAvatar.setImageBitmap(image)
        Snackbar.make(profileAvatar, "Image Saved!", Snackbar.LENGTH_LONG).show()
    }

    /**
     * Rotates bitmap image
     *
     */
    fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    companion object {
        fun newInstance() = ProfileFragment()
        const val TAG = "ProfileFragment"
        private const val CAMERA = 1
    }

}
