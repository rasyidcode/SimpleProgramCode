package me.jamilalrasyidis.simpleprogramcode.ui.detail2

//import me.jamilalrasyidis.simpleprogramcode.ui.detail.DetailViewModel
//import me.jamilalrasyidis.simpleprogramcode.ui.detail.ViewPagerAdapter
//
//package me.jamilalrasyidis.simpleprogramcode.ui.detail
//
//import android.os.Bundle
//import android.util.Log
//import android.view.MotionEvent
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.Observer
//import androidx.viewpager.widget.ViewPager
//import com.google.android.material.tabs.TabLayout
//import me.jamilalrasyidis.simpleprogramcode.R
//import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity
//import me.jamilalrasyidis.simpleprogramcode.databinding.ActivityDetailBinding
//import org.koin.android.viewmodel.ext.android.viewModel
//
//class DetailActivity : AppCompatActivity() {
//
//    private val viewModel by viewModel<DetailViewModel>()
//
//    private val binding by lazy {
//        DataBindingUtil.setContentView<ActivityDetailBinding>(
//            this,
//            R.layout.activity_detail
//        )
//    }
//
//    private val title by lazy { intent.extras?.getString("programTitle", "Detail Program") }
//
//    private val programId by lazy { intent.extras?.getString("programId", "program0") }
//
//    private val codesObserver by lazy {
//        Observer<List<CodeEntity>> {
//            Log.d(TAG, "data length : ${it.size}")
////            Log.d(TAG, "tab size : " + pagerAdapter.count)
//            if (it.isNotEmpty()) {
//                dataCodes = it
//                setupTabsAndPager(it)
//            }
////            if (dataCodes.isEmpty()) {
////                dataCodes = it
////            } else {
////                if (it.isNotEmpty()) {
////                    setupTabsAndPager(dataCodes)
////                }
////            }
//        }
//    }
//
//    private var dataCodes: List<CodeEntity> = emptyList()
//
//    private var isTabExist: Boolean = false
//
//    private val pagerAdapter by lazy {
//        ViewPagerAdapter(
//            supportFragmentManager,
//            dataCodes.size
//        )
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.title = title
//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        initiateViewModel()
//    }
//
//    private fun initiateViewModel() {
//        viewModel.getCodeByProgramId(programId!!).observe(this, codesObserver)
//    }
//
//    private fun setupTabsAndPager(codes: List<CodeEntity>) {
//        viewModel.currentNameAndCodeLiveData.postValue(listOf(codes[0].name, codes[0].codes))
//
//        if (!isTabExist) {
//            for (code in codes) {
//                binding.tabLayout.addTab(binding.tabLayout.newTab().setText(code.name))
//            }
//            isTabExist = true
//        }
//        binding.viewPager.offscreenPageLimit = codes.size - 1
//        binding.viewPager.enabledSwipe = false
//        binding.viewPager.adapter = pagerAdapter
////        binding.viewPager.addOnPageChangeListener(object :
////            TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout) {
////            override fun onPageSelected(position: Int) {
////                binding.viewPager.currentItem = position
////
////                viewModel.currentNameAndCodeLiveData.postValue(
////                    listOf(
////                        codes[position].name,
////                        codes[position].codes
////                    )
////                )
////            }
////        })
//        binding.tabLayout.addOnTabSelectedListener(object :
//            TabLayout.ViewPagerOnTabSelectedListener(binding.viewPager) {
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                super.onTabReselected(tab)
//                viewModel.currentNameAndCodeLiveData.postValue(
//                    listOf(
//                        codes[tab?.position!!].name,
//                        codes[tab.position].codes
//                    )
//                )
//                pagerAdapter.notifyDataSetChanged()
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                super.onTabSelected(tab)
//                binding.viewPager.currentItem = tab.position
//
//                viewModel.currentNameAndCodeLiveData.postValue(
//                    listOf(
//                        codes[tab.position].name,
//                        codes[tab.position].codes
//                    )
//                )
//
//                pagerAdapter.notifyDataSetChanged()
//            }
//        })
//    }
//
//    companion object {
//        const val TAG = "DetailActivity"
//    }
//
//}