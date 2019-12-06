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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity
import me.jamilalrasyidis.simpleprogramcode.databinding.FragmentProgramListBinding
import me.jamilalrasyidis.simpleprogramcode.extension.getSharedPreferencesName
import me.jamilalrasyidis.simpleprogramcode.extension.isConnectedToWifi
import me.jamilalrasyidis.simpleprogramcode.ui.ItemClickListener
import me.jamilalrasyidis.simpleprogramcode.ui.detail.DetailActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.toast
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

    private val adRequest by lazy {
        AdRequest.Builder()
            .addTestDevice(resources.getString(R.string.device_pocophone_id))
            .addTestDevice(resources.getString(R.string.device_asus_id))
            .build()
    }

    private val interstitialAd by lazy {
        InterstitialAd(requireContext()).apply {
            this.adUnitId = resources.getString(R.string.interstitial_ads_id)
            this.adListener = object : AdListener() {
                override fun onAdClosed() {
                    setupInterstitialAds()
                    navigateToDetail(programId, programTitle)
                    super.onAdClosed()
                }
            }
        }
    }

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun onClick(view: View, programs: List<ProgramEntity>, isLongClick: Boolean) {
                val itemPosition = binding.listProgram.getChildLayoutPosition(view)

                programId = programs[itemPosition].id
                programTitle = programs[itemPosition].title

                var currentCounter = sharedPref.getInt("adsCounter", 0)
                sharedPref.edit().apply {
                    currentCounter += 1
                    this?.putInt("adsCounter", currentCounter)
                }.apply()

                if (sharedPref.getBoolean("firstTimeDetail", true)) {
                    if ((activity as HomeActivity).isConnectedToWifi()) {
                        sharedPref.edit().putBoolean("firstTimeDetail", false).apply()

                        if (currentCounter >= 3) {
                            interstitialAd.show()

                            sharedPref.edit().apply {
                                this.putInt("adsCounter", 0)
                            }.apply()
                        } else {
                            navigateToDetail(programId, programTitle)
                        }
                    } else {
                        (activity as HomeActivity).toast("Currently you offline")
                    }
                } else {
                    if (currentCounter >= 3) {
                        interstitialAd.show()

                        sharedPref.edit().apply {
                            this.putInt("adsCounter", 0)
                        }.apply()
                    } else {
                        navigateToDetail(programId, programTitle)
                    }
                }
            }
        }
    }

    private lateinit var programId: String
    private lateinit var programTitle: String

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
        binding.bannerAds.loadAd(adRequest)
        setupInterstitialAds()

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

    private fun navigateToDetail(programId: String, programTitle: String) {
        (activity as HomeActivity).startActivity((activity as HomeActivity).intentFor<DetailActivity>().apply {
            putExtra("programId", programId)
            putExtra("programTitle", programTitle)
        })
    }

    private fun setupInterstitialAds() {
        if (!interstitialAd.isLoading && !interstitialAd.isLoaded) {
            interstitialAd.loadAd(adRequest)
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
            adapter.itemClickListener = itemClickListener

            binding.listProgram.adapter = adapter

            progressDialog.dismiss()
        })
    }

    companion object {
        const val TAG = "ProgramListFragment"
    }
}