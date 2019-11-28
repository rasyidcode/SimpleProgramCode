package me.jamilalrasyidis.simpleprogramcode.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import io.github.kbiakov.codeview.highlight.Font
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.FragmentCodeViewerBinding
import me.jamilalrasyidis.simpleprogramcode.extension.toCodeFormat
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlin.math.roundToInt

class CodeViewerFragment : Fragment() {

    private val viewModel by sharedViewModel<DetailViewModel>()

    private lateinit var binding: FragmentCodeViewerBinding

    private var currentFontSize: Int = 10

    private var currentData = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_code_viewer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCodeViewer()
        setupAction()
    }

    private fun setupAction() {
        binding.btnZoomIn.setOnClickListener {
            if (currentFontSize < 18) {
                currentFontSize++
                binding.codeView.setOptions(getCurrentOptions(currentData[0], currentData[1]))
            }
        }
        binding.btnZoomOut.setOnClickListener {
            if (currentFontSize > 6) {
                currentFontSize--
                binding.codeView.setOptions(getCurrentOptions(currentData[0], currentData[1]))
            }
        }
    }

    private fun setupCodeViewer() {
        viewModel.apply {
            currentNameAndCodeLiveData.observe(this@CodeViewerFragment, Observer {
                currentData.clear()
                currentData.add(it[0])
                currentData.add(it[1])
                binding.codeView.setOptions(getCurrentOptions(currentData[0], currentData[1]))
            })
        }
    }

    private fun getCurrentOptions(name: String, codes: String): Options {
        return Options.Default.get(requireContext())
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

    companion object {
        const val TAG = "CodeViewerFragment"
    }
}