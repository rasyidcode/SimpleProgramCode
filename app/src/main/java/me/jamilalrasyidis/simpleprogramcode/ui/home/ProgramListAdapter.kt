package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity
import me.jamilalrasyidis.simpleprogramcode.databinding.ItemProgramBinding
import me.jamilalrasyidis.simpleprogramcode.ui.ItemClickListener
import me.jamilalrasyidis.simpleprogramcode.ui.detail.DetailActivity
import org.jetbrains.anko.intentFor

class ProgramListAdapter : RecyclerView.Adapter<ProgramListAdapter.ViewHolder>() {

    lateinit var inflateType: InflateType

    lateinit var programList: List<ProgramEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProgramBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return programList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(programList[position])
    }

    inner class ViewHolder(private val binding: ItemProgramBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var clickListener: ItemClickListener? = null

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(program: ProgramEntity) {
            val ctx = binding.root.context
            binding.programTitle.text = program.title
            binding.programSubtitle.text = program.desc
            @SuppressLint("SetTextI18n")
            binding.textAvailableLanguage.text = "Language : ${program.availableLanguage}"
            clickListener = object : ItemClickListener {
                override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                    ctx.startActivity(ctx.intentFor<DetailActivity>().apply {
                        putExtra("programId", program.id)
                        putExtra("programTitle", program.title)
                    })
                }
            }
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

        override fun onClick(p0: View?) {
            clickListener?.onClick(p0!!, adapterPosition, false)
        }
    }

    companion object {
        const val TAG = "ProgramListAdapter"
    }
}


enum class InflateType {
    PROGRAM_LIST,
    FAVORITE,
    HISTORY
}