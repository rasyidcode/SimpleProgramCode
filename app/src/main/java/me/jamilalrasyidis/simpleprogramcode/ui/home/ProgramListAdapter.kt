package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity
import me.jamilalrasyidis.simpleprogramcode.databinding.ItemProgramBinding
import me.jamilalrasyidis.simpleprogramcode.extension.getSharedPreferencesName
import me.jamilalrasyidis.simpleprogramcode.extension.isConnectedToWifi
import me.jamilalrasyidis.simpleprogramcode.ui.ItemClickListener
import me.jamilalrasyidis.simpleprogramcode.ui.detail.DetailActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.toast

class ProgramListAdapter : RecyclerView.Adapter<ProgramListAdapter.ViewHolder>(), View.OnClickListener {

    lateinit var itemClickListener: ItemClickListener

    lateinit var context: Context

    lateinit var inflateType: InflateType

    lateinit var programList: List<ProgramEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProgramBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return programList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(programList[position])
    }

    inner class ViewHolder(private val binding: ItemProgramBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        private var clickListener: ItemClickListener? = null

//        init {
//            binding.root.setOnClickListener(this)
//        }

        fun bind(program: ProgramEntity) {
            binding.programTitle.text = program.title
            binding.programSubtitle.text = program.desc
            @SuppressLint("SetTextI18n")
            binding.textAvailableLanguage.text = "Language : ${program.availableLanguage}"
        }

//        private fun handleInflatedWidget() {
//            val scale = binding.root.resources.displayMetrics.density
//            val verticalPadding: Int = (8 * scale + 0.5f).toInt()
//            val horizontalPadding: Int = (16 * scale + 0.5f).toInt()
//            when (inflateType) {
//                InflateType.PROGRAM_LIST -> {
//                    binding.textTimeHumanReadable.visibility = View.GONE
//                    binding.programTitle.setPadding(
//                        horizontalPadding,
//                        verticalPadding,
//                        horizontalPadding,
//                        verticalPadding
//                    )
//                    binding.favIcon.visibility = View.GONE
//                }
//                InflateType.FAVORITE -> {
//                    binding.textTimeHumanReadable.visibility = View.VISIBLE
//                    binding.programTitle.setPadding(
//                        horizontalPadding,
//                        verticalPadding,
//                        horizontalPadding,
//                        verticalPadding
//                    )
//                    binding.favIcon.visibility = View.VISIBLE
//                }
//                InflateType.HISTORY -> {
//                    binding.textTimeHumanReadable.visibility = View.GONE
//                    binding.programTitle.setPadding(
//                        horizontalPadding,
//                        0,
//                        horizontalPadding,
//                        verticalPadding
//                    )
//                    binding.favIcon.visibility = View.INVISIBLE
//                }
//            }
//        }

//        override fun onClick(p0: View?) {
//            clickListener?.onClick(p0!!, adapterPosition, false)
//        }
    }

    companion object {
        const val TAG = "ProgramListAdapter"
    }

    override fun onClick(p0: View?) {
        itemClickListener.onClick(p0!!, programList, false)
    }
}


enum class InflateType {
    PROGRAM_LIST,
    FAVORITE,
    HISTORY
}