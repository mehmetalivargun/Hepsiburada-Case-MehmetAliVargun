package com.mehmetalivargun.hepsiburadacase.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.hepsiburadacase.data.model.SearchSoftwareItem
import com.mehmetalivargun.hepsiburadacase.data.model.SearchTrackItem
import com.mehmetalivargun.hepsiburadacase.databinding.ItemSearchBinding
import com.mehmetalivargun.hepsiburadacase.extentions.getDate
import com.mehmetalivargun.hepsiburadacase.extentions.load
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType


abstract class BaseSearchAdapter<T : Any>  constructor(diffUtil: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, BaseSearchAdapter<T>.ResultViewHolder>(diffUtil) {
    private var onItemClickListener: ((T) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ResultViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
    fun setOnItemClickListener(listener: (T) -> Unit) {
        onItemClickListener = listener
    }


    inner class ResultViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) = binding.apply{
            when(item){
                is SearchTrackItem ->{
                    item.artworkUrl100?.let { artWork.load(it) }
                   collectionName.text=item.trackName
                    releaseDate.text=item.releaseDate.getDate()
                    collectionPrice.text="$ "+ if(item.kind==EntityType.BOOKS.value.enumValue) item.price.toString() else item.collectionPrice.toString()
                }
                is SearchSoftwareItem->{
                    item.artworkUrl100?.let { artWork.load(it) }
                    collectionName.text=item.trackName
                    releaseDate.text=item.releaseDate.getDate()
                    collectionPrice.text=if(item.price==0.0) "free" else "$ "+item.price.toString()
                }
            }
            binding.root.setOnClickListener{
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }
}