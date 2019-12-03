package me.jamilalrasyidis.simpleprogramcode.ui.detail

import android.Manifest
import android.app.DownloadManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import io.github.kbiakov.codeview.highlight.Font
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity
import me.jamilalrasyidis.simpleprogramcode.databinding.ActivityDetailBinding
import me.jamilalrasyidis.simpleprogramcode.extension.convertToFileType
import me.jamilalrasyidis.simpleprogramcode.extension.setOnTabSelected
import me.jamilalrasyidis.simpleprogramcode.extension.toCodeFormat
import me.jamilalrasyidis.simpleprogramcode.extension.toFileNameStyle
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import kotlin.math.roundToInt


class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailViewModel>()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityDetailBinding>(
            this,
            R.layout.activity_detail
        )
    }

    private val title by lazy { intent.extras?.getString("programTitle", "Detail Program") }

    private val programId by lazy { intent.extras?.getString("programId", "program0") }

    private var currentFontSize: Int = 10

    private var currentCodes: CodeEntity? = null

    private var isTabExist: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = title
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initiateViewModel()
        setupAction()
    }

    private fun initiateViewModel() {
        viewModel.getCodeByProgramId(programId!!).observe(this, Observer<List<CodeEntity>> {
            if (it.isNotEmpty()) {
                setupTabs(it)
            }
        })
    }

    private fun setupTabs(codes: List<CodeEntity>) {
        currentCodes = codes[0]

        if (!isTabExist) {
            for (code in codes) {
                binding.tabLayout.addTab(binding.tabLayout.newTab().setText(code.name))
            }

            updateCodeUI(codes[0])
            updateButtonFavoriteUI(codes[0].isFavored)

            isTabExist = true
        }

        binding.tabLayout.setOnTabSelected {
            currentCodes = codes[it?.position!!]

            updateCodeUI(currentCodes!!)
            updateButtonFavoriteUI(currentCodes!!.isFavored)
        }
    }

    private fun setupAction() {
        binding.btnZoomIn.setOnClickListener {
            if (currentFontSize < 18) {
                currentFontSize++
                updateCodeUI(currentCodes!!)
            }
        }
        binding.btnZoomOut.setOnClickListener {
            if (currentFontSize > 6) {
                currentFontSize--
                updateCodeUI(currentCodes!!)
            }
        }
        binding.btnCopy.setOnClickListener {
            val clipboard: ClipboardManager =
                this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("codes", currentCodes?.codes)
            clipboard.setPrimaryClip(clip)

            toast("Code copied to clipboard")
        }
        binding.btnFavorite.setOnClickListener {
            viewModel.updateFavorite(!(currentCodes!!.isFavored), currentCodes!!.id)
            (it as ImageView).setImageResource(getImageResources(!(currentCodes!!.isFavored)))
            currentCodes!!.isFavored = !currentCodes!!.isFavored
        }
        binding.btnDownload.setOnClickListener {
            if (isStoragePermissionGranted()) {
                generateCodeOnSD(
                    "${title?.toFileNameStyle()}${currentCodes?.name?.convertToFileType()}",
                    currentCodes?.codes!!
                )
            } else {
                longToast("You need to give access to storage in order to get the code in file mode.")
            }
        }
        binding.btnShare.setOnClickListener {
            if (isStoragePermissionGranted()) {
                val intentShare = Intent(Intent.ACTION_SEND)
                intentShare.type = "file/*"
                @Suppress("DEPRECATION")
                intentShare.putExtra(
                    Intent.EXTRA_STREAM,
                    Uri.parse("file://${Environment.getExternalStorageDirectory().path + "/codes/${title?.toFileNameStyle()}${currentCodes?.name?.convertToFileType()}"}")
                )
                intentShare.putExtra(Intent.EXTRA_SUBJECT, "Simple Program Code")
                intentShare.putExtra(Intent.EXTRA_TEXT, "Code")
                startActivity(Intent.createChooser(intentShare, "Share Code"))
            }
        }
    }

    private fun getImageResources(isFavored: Boolean): Int {
        return if (isFavored) {
            R.drawable.ic_favorite_red_24dp
        } else {
            R.drawable.ic_favorite_border_white_24dp
        }
    }

    private fun getCurrentOptions(name: String, codes: String): Options {
        return Options.Default.get(this)
            .withLanguage(name)
            .withCode(codes.toCodeFormat())
            .withFont(Font.Consolas)
            .withFormat(
                Format(
                    1f,
                    (currentFontSize + currentFontSize * 0.3).roundToInt(),
                    3,
                    currentFontSize.toFloat()
                )
            )
            .withTheme(ColorTheme.MONOKAI)
    }

    private fun updateCodeUI(code: CodeEntity) {
        binding.codeView.setOptions(getCurrentOptions(code.name, code.codes))
    }

    private fun updateButtonFavoriteUI(isFavored: Boolean) {
        binding.btnFavorite.setImageResource(getImageResources(isFavored))
    }

    private fun generateCodeOnSD(filename: String, content: String) {
        @Suppress("DEPRECATION")
        try {
            val root = File(Environment.getExternalStorageDirectory(), "codes")
            if (!root.exists()) {
                root.mkdirs()
            }
            val file = File(root, filename)
            val writer = FileWriter(file)
            writer.append(content)
            writer.flush()
            writer.close()
            Snackbar.make(binding.root, "Your file saved on storage", Snackbar.LENGTH_LONG)
                .setAction("Open File") {
                    //                    val intent = Intent(Intent.ACTION_VIEW)
//                    val uri = Uri.parse(Environment.getExternalStorageDirectory().path + "/codes/${title?.toFileNameStyle()}${currentCodes?.name?.convertToFileType()}")
//                    intent.setDataAndType(uri, "file/java")
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    this.startActivity(intent)
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    val uri =
                        Uri.parse(Environment.getExternalStorageDirectory().path + "/codes/${title?.toFileNameStyle()}${currentCodes?.name?.convertToFileType()}")
                    intent.setDataAndType(uri, "file/*")
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    startActivity(Intent(Intent.createChooser(intent, "Browse File")))
                }
                .show()
        } catch (e: Exception) {
            Log.d(TAG, "something went wrong!")
            Log.d(TAG, "error : ${e.message}")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            generateCodeOnSD(
                "${title?.toFileNameStyle()}${currentCodes?.name?.convertToFileType()}",
                currentCodes?.codes!!
            )
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    1
                )
                false
            }
        } else {
            true
        }
    }

    companion object {
        const val TAG = "DetailActivity"
    }

}