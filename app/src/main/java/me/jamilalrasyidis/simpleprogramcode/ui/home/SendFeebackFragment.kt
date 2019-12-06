package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.FragmentSendFeedbackBinding
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.toast

class SendFeedbackFragment : Fragment() {

    private lateinit var binding: FragmentSendFeedbackBinding

    private val database by lazy { FirebaseFirestore.getInstance() }

    private val feedbackRef by lazy { database.collection("feedback") }

    @Suppress("DEPRECATION")
    private val progressDialog by lazy {
        (activity as HomeActivity).progressDialog("Please wait...", "Sending your feedback") {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_feedback, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSend.setOnClickListener {
            progressDialog.show()
            if (!isValidField()) {
                val textSubject = binding.editSubject.text.toString()
                val textMessage = binding.editMessage.text.toString()
                sendFeedback(textSubject, textMessage)
            } else {
                if (binding.editSubject.text.isNullOrBlank()) {
                    @Suppress("DEPRECATION")
                    binding.editSubject.error = "Subject should not be empty"
                }

                if (binding.editMessage.text.isNullOrBlank()) {
                    binding.editMessage.error = "Message should no be empty!"
                }
            }
        }
    }

    private fun isValidField() : Boolean {
        return binding.editSubject.text.isNullOrBlank() && binding.editMessage.text.isNullOrBlank()
    }

    private fun sendFeedback(subject: String, message: String) {
        val data = hashMapOf(
            "subject" to subject,
            "message" to message,
            "app_from" to (activity as HomeActivity).packageName
        )
        feedbackRef.document()
            .set(data)
            .addOnSuccessListener {
                Handler().postDelayed({
                    progressDialog.dismiss()
                    binding.editSubject.text?.clear()
                    binding.editMessage.text?.clear()
                    (activity as HomeActivity).toast("Feedback sent!")
                }, 2000)
            }
            .addOnFailureListener {
                Handler().postDelayed({
                    progressDialog.dismiss()
                    binding.editSubject.text?.clear()
                    binding.editMessage.text?.clear()
                    (activity as HomeActivity).toast("Something went wrong, please try again!")
                }, 2000)
            }
    }

    private fun sendMail(subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, "ajapro07@gmail.com")
        intent.putExtra(Intent.EXTRA_CC, "rasyidcode7@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            activity?.toast("There is no email client installed.")
        }
    }

}