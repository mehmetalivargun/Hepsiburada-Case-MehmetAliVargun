package com.mehmetalivargun.hepsiburadacase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.hepsiburadacase.data.model.SearchItem
import com.mehmetalivargun.hepsiburadacase.databinding.ItemSearchBinding
import com.mehmetalivargun.hepsiburadacase.extentions.getDate
import com.mehmetalivargun.hepsiburadacase.extentions.load
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType

class SearchAdapter constructor(diffUtil: DiffUtil.ItemCallback<SearchItem>) :
    PagingDataAdapter<SearchItem, SearchAdapter.ResultViewHolder>(diffUtil) {
    private var onItemClickListener: ((SearchItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ResultViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun setOnItemClickListener(listener: (SearchItem) -> Unit) {
        onItemClickListener = listener
    }


    inner class ResultViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchItem) = binding.apply {
            item.artworkUrl100?.let { artWork.load(it) }
            collectionName.text = item.trackName
            releaseDate.text = item.releaseDate.getDate()
            val price = when (item.kind) {
                EntityType.MUSIC.value.enumValue -> item.price
                EntityType.MOVIES.value.enumValue -> item.price
                else -> item.price
            }
            val priceText = when (price) {
                null -> "Not Available"
                0.0 -> "Free"
                else -> "$ " + price.toString()
            }
            collectionPrice.text = priceText
            root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }

        }
    }
}