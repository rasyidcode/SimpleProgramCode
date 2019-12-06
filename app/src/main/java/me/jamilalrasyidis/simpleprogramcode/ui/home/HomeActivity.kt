package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.ads.MobileAds
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.ActivityHomeBinding
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        )
    }

    private val toggle by lazy {
        ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
    }

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Home"

        setupDrawer()
        setupNavigation()
        initViewModel()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProgramListFragment())
                .commit()
            binding.navView.setCheckedItem(R.id.program_list_screen)
        }
    }

    private fun initViewModel() {
        viewModel.programs.observe(this, Observer {})
    }

    private fun setupNavigation() {
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.program_list_screen -> {
                    replaceFragment(ProgramListFragment())
                    true
                }
                R.id.submit_code_screen -> {
                    toast("Coming soon!")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.favorite_screen -> {
                    replaceFragment(FavoriteFragment())
                    true
                }
                R.id.history_screen -> {
                    toast("Coming soon!")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.privacy_policy_screen -> {
                    replaceFragment(PrivacyPolicyFragment())
                    true
                }
                R.id.rate_app -> {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                    )
                    true
                }
                R.id.more_apps -> {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/developer?id=RasyidCODE")
                        )
                    )
                    true
                }
                R.id.share_apps -> {
                    try {
                        val intentShare = Intent(Intent.ACTION_SEND)
                        val messages =
                            "\nYou can find a lot of code example in various language\n" +
                                    "What are you waiting for?\n" +
                                    "You can install it by visit the link below:\n" +
                                    "https://play.google.com/store/apps/details?id=${packageName}"
                        intentShare.type = "text/plain"
                        intentShare.putExtra(
                            Intent.EXTRA_SUBJECT,
                            resources.getString(R.string.app_name)
                        )
                        intentShare.putExtra(Intent.EXTRA_TEXT, messages)
                        startActivity(Intent.createChooser(intentShare, "Choose One"))
                    } catch (e: Exception) {
                        toast("Something went wrong, please try again!")
                    }
                    true
                }
                R.id.send_feedback_screen -> {
                    replaceFragment(SendFeedbackFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setupDrawer() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.drawerLayout.addDrawerListener(toggle)
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                supportActionBar?.title = "Simple Program Code"
                toggle.syncState()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                when (supportFragmentManager.findFragmentById(R.id.fragment_container)) {
                    is ProgramListFragment -> supportActionBar?.title =
                        "Home"
                    is FavoriteFragment -> supportActionBar?.title =
                        "Favorites"
                    is HistoryFragment -> supportActionBar?.title =
                        "History"
                    is PrivacyPolicyFragment -> supportActionBar?.title =
                        "Privacy Policy"
                    is SendFeedbackFragment -> supportActionBar?.title =
                        "Send Feedback"
                }
                toggle.syncState()
            }
        })
        binding.drawerLayout.post {
            toggle.syncState()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else when {
            supportFragmentManager.findFragmentById(R.id.fragment_container) is ProgramListFragment -> {
                super.onBackPressed()
            }
            else -> {
                replaceFragment(ProgramListFragment())
                supportActionBar?.title = "Home"
                binding.navView.setCheckedItem(R.id.program_list_screen)
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    companion object {
        const val TAG = "HomeActivity"
    }

}