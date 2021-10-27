package com.mehmetalivargun.hepsiburadacase.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.hepsiburadacase.data.model.Result
import com.mehmetalivargun.hepsiburadacase.data.model.SearchResultItemResponse
import com.mehmetalivargun.hepsiburadacase.databinding.ItemSearchBinding
import com.mehmetalivargun.hepsiburadacase.extentions.load
import javax.inject.Inject

class SearchAdapter @Inject constructor() :
    PagingDataAdapter<SearchResultItemResponse, SearchAdapter.ResultViewHolder>(ResultComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ResultViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ResultViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchResultItemResponse) = binding.apply{
            Log.e("Test",item.artworkUrl100)
            imageView.load(imgUrl = item.artworkUrl100)
        }
    }

    object ResultComparator : DiffUtil.ItemCallback<SearchResultItemResponse>() {
        override fun areItemsTheSame(oldItem: SearchResultItemResponse, newItem: SearchResultItemResponse) =
            oldItem.artworkUrl100 == newItem.artworkUrl100

        override fun areContentsTheSame(oldItem: SearchResultItemResponse, newItem: SearchResultItemResponse) =
            oldItem == newItem
    }
}