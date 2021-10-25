package com.mehmetalivargun.hepsiburadacase.ui.search

import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.viewModels
import com.mehmetalivargun.hepsiburadacase.R
import com.mehmetalivargun.hepsiburadacase.base.BaseFragment
import com.mehmetalivargun.hepsiburadacase.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel: SearchViewModel by viewModels()

    override fun FragmentSearchBinding.initialize() {
        viewModel.search("allame","")
        binding.filterRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val seleted = radioGroup.findViewById<RadioButton>(i).text

            binding.emojiTextView.text = when (seleted) {
                "Books" -> "🗡️"
                "Movies" -> "🏹"
                "Apps" -> "🐲"
                else -> "💀"
            }
        }



    }




}