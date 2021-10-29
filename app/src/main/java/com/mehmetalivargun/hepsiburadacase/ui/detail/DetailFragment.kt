package com.mehmetalivargun.hepsiburadacase.ui.detail

import android.widget.Toast
import com.mehmetalivargun.hepsiburadacase.base.BaseFragment
import com.mehmetalivargun.hepsiburadacase.data.model.SearchSoftwareItem
import com.mehmetalivargun.hepsiburadacase.data.model.SearchTrackItem
import com.mehmetalivargun.hepsiburadacase.databinding.FragmentDetailBinding
import com.mehmetalivargun.hepsiburadacase.databinding.FragmentSearchBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    override fun FragmentDetailBinding.initialize() {
        val args = arguments?.let {
            DetailFragmentArgs.fromBundle(
                it
            )
        }
        var data:SearchTrackItem?=null
        var data2:SearchSoftwareItem?=null
        args?.apply {
             data = searchTracktArgument
             data2 = searchSoftwareArgument
        }


            Toast.makeText(requireContext(), data?.kind.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), data2?.sellerName.toString(), Toast.LENGTH_SHORT).show()

    }
}