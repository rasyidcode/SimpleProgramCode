package me.jamilalrasyidis.simpleprogramcode.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import io.github.kbiakov.codeview.highlight.Font
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.ActivityViewCodeBinding
import me.jamilalrasyidis.simpleprogramcode.extension.toCodeFormat
import kotlin.math.roundToInt

class ViewCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewCodeBinding

    private val titleCode by lazy { intent.extras?.getString("title", "Detail Program") }

    private val code by lazy { intent.extras?.getString("code", "nope") }

    private val language by lazy { intent.extras?.getString("language", "php") }

    private val adRequest by lazy {
        AdRequest.Builder()
            .addTestDevice(resources.getString(R.string.device_pocophone_id))
            .addTestDevice(resources.getString(R.string.device_asus_id))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_code)
        binding.bannerAds.loadAd(adRequest)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = titleCode
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.codeView.setOptions(
            Options.Default.get(this)
                .withLanguage(language!!)
                .withCode(code?.toCodeFormat()!!)
                .withFont(Font.Consolas)
                .withFormat(
                    Format(
                        1f,
                        (10 + 10 * 0.3).roundToInt(),
                        3,
                        10.toFloat()
                    )
                )
                .withTheme(ColorTheme.MONOKAI)
        )
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}