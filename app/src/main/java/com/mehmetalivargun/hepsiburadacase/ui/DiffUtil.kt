package com.mehmetalivargun.hepsiburadacase.ui

import androidx.recyclerview.widget.DiffUtil
import com.mehmetalivargun.hepsiburadacase.data.model.SearchItem

object DiffUtil {
   val searchResultDiffUtil:DiffUtil.ItemCallback<SearchItem> =object : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) =
            oldItem.trackId == newItem.trackId

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem) =
            oldItem == newItem
    }
}