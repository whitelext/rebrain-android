package screen.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import com.whitelext.foodapp.FoodApplication
import com.whitelext.foodapp.R
import di.DaggerProfileFragmentComponent
import di.ProfileFragmentModule
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_profile.*
import screen.login.LoginActivity
import screen.main.viewmodel.ProfileViewModel
import screen.maps.MapsActivity
import utils.BaseFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Fragment for showing profile screen
 *
 */
class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: ProfileViewModel

    private lateinit var currentPhotoPath: String

    private val profileCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component =
            DaggerProfileFragmentComponent.builder()
                .appComponent(((this.requireActivity().application) as FoodApplication).getAppComponent())
                .profileFragmentModule(ProfileFragmentModule(this))
                .build()
        component.inject(this)
        viewModel.getUserInfo()
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle(getString(R.string.pictureDialogTitle))
        val pictureDialogItems = arrayOf(getString(R.string.camera_photo_dialog))
        pictureDialog.setItems(
            pictureDialogItems
        ) { _, index ->
            when (index) {
                0 -> takePictureFromCamera()
            }
        }
        pictureDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.setUserImage(currentPhotoPath)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun takePictureFromCamera() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Ensure that there's a camera activity to handle the intent
        takePictureIntent.resolveActivity(requireActivity().packageManager)
        // Create the File where the photo should go
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            // Error occurred while creating the File
            null
        }
        // Continue only if the File was successfully created
        photoFile?.let {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.whitelext.foodapp",
                it
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onDestroy() {
        profileCompositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        profileCompositeDisposable.add(profileAvatar.clicks().subscribe {
            showPictureDialog()
        })
        profileCompositeDisposable.add(profilePickUpButton.clicks().subscribe {
            MapsActivity.start(requireContext())
        })
        profileCompositeDisposable.add(profileExitButton.clicks().subscribe {
            viewModel.logout()
        })


    }

    private fun initViewModel() {

        viewModel.imageLoadingResult.observe(viewLifecycleOwner, Observer { loadingResult ->
            image_progressBar.isVisible = loadingResult.isLoading
            profileAvatar.isVisible = !loadingResult.isLoading

            loadingResult.success?.let { imagePath ->
                updateUserImage(imagePath)
            }
        })

        viewModel.showErrorMessage.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                showImageUploadFailed(it)
            }
        })

        viewModel.showSuccessMessage.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                showImageUploadSuccess(it)
            }
        })

        viewModel.showLogoutSuccess.observe(viewLifecycleOwner, Observer {
            showLogoutSuccess()
        })

        viewModel.showLogoutFailureMessage.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                showLogoutFailed(it)
            }
        })

        viewModel.userLoadingResult.observe(viewLifecycleOwner, Observer { loadingResult ->
            image_progressBar.isVisible = loadingResult.isLoading
            profileAvatar.isVisible = !loadingResult.isLoading
            profileName.isVisible = !loadingResult.isLoading

            loadingResult.success?.let { user ->
                profileName.text = user.name
            }
        })
    }


    private fun showImageUploadFailed(@StringRes errorString: Int) {
        Snackbar.make(profileAvatar, errorString, Snackbar.LENGTH_LONG).show()
    }

    private fun showImageUploadSuccess(@StringRes successString: Int) {
        Snackbar.make(profileAvatar, successString, Snackbar.LENGTH_LONG).show()
    }

    private fun showLogoutFailed(@StringRes errorString: Int) {
        Snackbar.make(profileAvatar, errorString, Snackbar.LENGTH_LONG).show()
    }

    private fun showLogoutSuccess() {
        LoginActivity.start(requireContext())
    }

    private fun updateUserImage(filePath: String) {
        val bitmap = BitmapFactory.decodeFile(filePath).rotate(90f)
        val roundedBitmap = RoundedBitmapDrawableFactory.create(resources, bitmap)
        roundedBitmap.isCircular = true
        profileAvatar.setImageDrawable(roundedBitmap)
    }

    /**
     * Rotates bitmap image
     *
     */
    private fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    companion object {
        fun newInstance() = ProfileFragment()
        const val TAG = "ProfileFragment"
        private const val CAMERA_REQUEST_CODE = 1
    }

}
