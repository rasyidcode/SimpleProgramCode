package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.FragmentSendFeedbackBinding

class SendFeebackFragment : Fragment() {

    private lateinit var binding: FragmentSendFeedbackBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_feedback, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}