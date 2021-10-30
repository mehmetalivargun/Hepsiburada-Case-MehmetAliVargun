package com.mehmetalivargun.hepsiburadacase.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mehmetalivargun.hepsiburadacase.base.BaseFragment
import com.mehmetalivargun.hepsiburadacase.databinding.FragmentDetailBinding
import com.mehmetalivargun.hepsiburadacase.extentions.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    override fun FragmentDetailBinding.initialize() {
        Log.e("Result", args.kind!!)
        Log.e("Result", args.id.toString())
        viewModel.isLoading.observe(viewLifecycleOwner, {
            Log.e("ResultObserver", it.toString())
        })
        when (args.kind) {
            "software" -> {
                viewModel.softwareResult.observe(viewLifecycleOwner, {
                    it.artworkUrl512?.let { it1 -> binding.artWork.load(it1) }
                })
            }
            else -> {
                viewModel.result.observe(viewLifecycleOwner, {
                    it.artworkUrl100?.let { it1 -> binding.artWork.load(it1) }
                })
            }
        }
    }


}