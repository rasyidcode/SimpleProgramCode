package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.ActivityHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host) }

    private lateinit var appBarConfiguration: AppBarConfiguration

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

        setupDrawer()

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupNavigation()
        setupActionBar()

        initViewModel()
    }

    private fun setupActionBar() {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initViewModel() {
        viewModel.programs.observe(this, Observer {})
    }

    private fun setupNavigation() {
        binding.navView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.program_list_screen,
                R.id.favorite_screen,
                R.id.history_screen,
                R.id.privacy_policy_screen,
                R.id.send_feedback_screen
            ),
            binding.drawerLayout
        )
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
                when (navController.currentDestination?.id) {
                    R.id.program_list_screen -> supportActionBar?.title =
                        "Home"
                    R.id.favorite_screen -> supportActionBar?.title =
                        "Favorite Program"
                    R.id.history_screen -> supportActionBar?.title =
                        "History"
                    R.id.privacy_policy_screen -> supportActionBar?.title =
                        "Privacy Policy"
                    R.id.send_feedback_screen -> supportActionBar?.title =
                        "Send Feedback"
                }
                toggle.syncState()
            }
        })
        binding.drawerLayout.post {
            toggle.syncState()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host)) || super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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