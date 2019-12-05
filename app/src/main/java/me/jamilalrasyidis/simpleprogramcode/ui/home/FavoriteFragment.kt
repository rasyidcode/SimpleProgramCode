package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.FragmentFavoriteBinding
import me.jamilalrasyidis.simpleprogramcode.extension.countNewLine
import me.jamilalrasyidis.simpleprogramcode.ui.detail.DetailViewModel
import org.jetbrains.anko.progressDialog
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FavoriteFragment : Fragment() {

    private val viewModel by viewModel<DetailViewModel>()

    private val favoriteListAdapter by lazy { FavoriteListAdapter() }

    private lateinit var binding: FragmentFavoriteBinding

    private var favoritesData = mutableListOf<FavoriteData>()

    @Suppress("DEPRECATION")
    private val progressDialog by lazy {
        (activity as HomeActivity).progressDialog("Please wait...", "Load Data Favorite") {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        return binding.root
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog.show()

        viewModel.getAllFavorites().observe(this, Observer { codes ->
            if (codes.isNotEmpty()) {
                for (code in codes) {
                    viewModel.getProgramsWithCodes(code.programId).observe(this, Observer {
                        if (favoritesData.size < codes.size) {
                            favoritesData.add(
                                FavoriteData(
                                    title = "${it.programEntity.title} - ${code.name}",
                                    lineTotal = code.codes.countNewLine(),
                                    language = code.name,
                                    code = code.codes,
                                    favoriteAt = ""
                                )
                            )
                        }
                    })
                }

                favoriteListAdapter.favorites = favoritesData
                binding.listProgramFavorite.setDivider(R.drawable.program_list_divider)
                binding.listProgramFavorite.layoutManager = LinearLayoutManager(requireContext())
                binding.listProgramFavorite.adapter = favoriteListAdapter
            } else {
                binding.listProgramFavorite.visibility = View.GONE
                binding.emptyFavorite.visibility = View.VISIBLE
            }

            progressDialog.dismiss()
        })
    }

    companion object {
        const val TAG = "FavoriteFragment"
    }
}