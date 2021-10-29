package com.mehmetalivargun.hepsiburadacase.ui

import androidx.recyclerview.widget.DiffUtil
import com.mehmetalivargun.hepsiburadacase.data.model.SearchSoftwareItem
import com.mehmetalivargun.hepsiburadacase.data.model.SearchTrackItem

object DiffUtil {
   val searchResultDiffUtil:DiffUtil.ItemCallback<SearchTrackItem> =object : DiffUtil.ItemCallback<SearchTrackItem>() {
        override fun areItemsTheSame(oldItem: SearchTrackItem, newItem: SearchTrackItem) =
            oldItem.artworkUrl100 == newItem.artworkUrl100

        override fun areContentsTheSame(oldItem: SearchTrackItem, newItem: SearchTrackItem) =
            oldItem == newItem
    }

    val searchAppDiffUtil:DiffUtil.ItemCallback<SearchSoftwareItem> =object : DiffUtil.ItemCallback<SearchSoftwareItem>() {
        override fun areItemsTheSame(oldItem: SearchSoftwareItem, newItem: SearchSoftwareItem) =
            oldItem.trackId == newItem.trackId

        override fun areContentsTheSame(oldItem: SearchSoftwareItem, newItem: SearchSoftwareItem) =
            oldItem == newItem
    }
}