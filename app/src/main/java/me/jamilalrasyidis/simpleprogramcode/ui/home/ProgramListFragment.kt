package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.FragmentProgramListBinding
import me.jamilalrasyidis.simpleprogramcode.extension.getSharedPreferencesName
import me.jamilalrasyidis.simpleprogramcode.extension.isConnectedToWifi
import org.jetbrains.anko.progressDialog
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ProgramListFragment : Fragment() {

    private lateinit var binding: FragmentProgramListBinding

    private val viewModel by sharedViewModel<HomeViewModel>()

    private val adapter by lazy { ProgramListAdapter().apply { context = (activity as HomeActivity) } }

    @Suppress("DEPRECATION")
    private val progressDialog by lazy {
        (activity as HomeActivity).progressDialog("Please wait...", "Load Data Code") {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
        }
    }

    private val sharedPref by lazy { (activity as HomeActivity).getSharedPreferences((activity as HomeActivity).getSharedPreferencesName(), 0) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog.show()

        if (sharedPref.getBoolean("first_time", true)) {
            if ((activity as HomeActivity).isConnectedToWifi()) {
                showListProgram(true)
                setupProgramList()

                sharedPref.edit().apply {
                    putBoolean("first_time", false)
                }.apply()
            } else {
                showListProgram(false)
                progressDialog.dismiss()
                binding.buttonReload.setOnClickListener {
                    viewModel.runGetProgramsAgain()
                    progressDialog.show()
                    Handler().postDelayed({
                        progressDialog.dismiss()
                        (activity as HomeActivity).recreate()
                    }, 3000)
                }
            }
        } else {
            showListProgram(true)
            setupProgramList()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            }, 3000)
        }
    }

    private fun showListProgram(show: Boolean) {
        return if (!show) {
            binding.noConnectionLayout.visibility = View.VISIBLE
            binding.swipeRefreshLayout.visibility = View.GONE
        } else {
            binding.swipeRefreshLayout.visibility = View.VISIBLE
            binding.noConnectionLayout.visibility = View.GONE
        }
    }

    private fun setupProgramList() {
        binding.listProgram.layoutManager = LinearLayoutManager(requireContext())
        binding.listProgram.setDivider(R.drawable.program_list_divider)
        viewModel.programs.observe(this, Observer {
            adapter.programList = it
            adapter.inflateType = InflateType.PROGRAM_LIST

            binding.listProgram.adapter = adapter

            progressDialog.dismiss()
        })
    }

    companion object {
        const val TAG = "ProgramListFragment"
    }
}