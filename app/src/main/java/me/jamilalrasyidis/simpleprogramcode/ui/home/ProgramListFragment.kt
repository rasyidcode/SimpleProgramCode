package me.jamilalrasyidis.simpleprogramcode.ui.home

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
import org.jetbrains.anko.progressDialog
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ProgramListFragment : Fragment() {

    private lateinit var binding: FragmentProgramListBinding

    private val viewModel by sharedViewModel<HomeViewModel>()

    private val adapter by lazy { ProgramListAdapter() }

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

        setupProgramList()

        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            }, 3000)
        }
    }

    private fun setupProgramList() {
        binding.listProgram.layoutManager = LinearLayoutManager(requireContext())
        binding.listProgram.setDivider(R.drawable.program_list_divider)
        viewModel.programs.observe(this, Observer {
            adapter.programList = it
            adapter.inflateType = InflateType.PROGRAM_LIST

            binding.listProgram.adapter = adapter
        })
    }

    companion object {
        const val TAG = "ProgramListFragment"
    }
}