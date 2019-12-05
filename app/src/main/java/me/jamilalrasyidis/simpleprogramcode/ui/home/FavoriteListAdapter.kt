package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.jamilalrasyidis.simpleprogramcode.R
import me.jamilalrasyidis.simpleprogramcode.databinding.ItemCodeBinding
import me.jamilalrasyidis.simpleprogramcode.extension.getColorLanguage
import me.jamilalrasyidis.simpleprogramcode.ui.detail.ViewCodeActivity
import org.jetbrains.anko.intentFor

class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

    lateinit var favorites: List<FavoriteData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCodeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    class ViewHolder(val binding: ItemCodeBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "DefaultLocale")
        fun bind(data: FavoriteData) {
            @Suppress("DEPRECATION")
            binding.languageColor.setBackgroundColor(Color.parseColor(data.language.getColorLanguage()))
            binding.textTitle.text = data.title.toLowerCase()
            binding.textTotalLine.text = "${data.lineTotal} lines"
            binding.btnCode.setOnClickListener {
                val ctx = binding.root.context
                ctx.startActivity(
                    ctx.intentFor<ViewCodeActivity>().putExtra(
                        "title",
                        data.title
                    ).putExtra("code", data.code).putExtra("language", data.language)
                )
            }
        }

    }

}

data class FavoriteData(
    var title: String,
    var lineTotal: Int,
    var code: String,
    var language: String,
    var favoriteAt: String
)