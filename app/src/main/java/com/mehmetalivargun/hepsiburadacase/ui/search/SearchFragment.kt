package com.mehmetalivargun.hepsiburadacase.ui.search

import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mehmetalivargun.hepsiburadacase.base.BaseFragment
import com.mehmetalivargun.hepsiburadacase.databinding.FragmentSearchBinding
import com.mehmetalivargun.hepsiburadacase.util.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel: SearchViewModel by viewModels()

    override fun FragmentSearchBinding.initialize() {
      //  viewModel.search("allame","")

        val adapter = SearchAdapter()

        binding.resultsRV.adapter=adapter
        with(binding){
            with(adapter){
                resultsRV.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }
                resultsRV.adapter=withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )
            }
        }
        binding.filterRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val seleted = radioGroup.findViewById<RadioButton>(i).text

            binding.emojiTextView.text = when (seleted) {
                "Books" -> "🗡️"
                "Movies" -> "🏹"
                "Apps" -> "🐲"
                else -> "💀"
            }
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }



    }




}