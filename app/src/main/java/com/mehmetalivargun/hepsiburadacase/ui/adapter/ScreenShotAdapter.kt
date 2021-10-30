package com.mehmetalivargun.hepsiburadacase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.hepsiburadacase.databinding.ItemScreenShotBinding
import com.mehmetalivargun.hepsiburadacase.extentions.load

class ScreenShotAdapter(private val screenShots: List<String>) :
    RecyclerView.Adapter<ScreenShotAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemScreenShotBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.imageView.load(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemScreenShotBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(screenShots[position])
    }

    override fun getItemCount() = screenShots.size


}

