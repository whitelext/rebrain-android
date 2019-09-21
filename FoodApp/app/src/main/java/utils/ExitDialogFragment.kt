package utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.foodapp.R
import kotlinx.android.synthetic.main.custom_exit_dialog.view.*
/*
    Class for working with Exit Dialogue for MainActivity
 */
class ExitDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialog = inflater.inflate(R.layout.custom_exit_dialog,null)
            dialog.dialog_positiveButton.setOnClickListener {
                activity?.finish()
            }
            dialog.dialog_negativeButton.setOnClickListener {
                this.dismiss()
            }
            builder.setView(dialog)
            return builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}