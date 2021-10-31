package com.mehmetalivargun.hepsiburadacase.ui.search

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.RadioButton
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mehmetalivargun.hepsiburadacase.R
import com.mehmetalivargun.hepsiburadacase.base.BaseFragment
import com.mehmetalivargun.hepsiburadacase.ui.adapter.SearchAdapter
import com.mehmetalivargun.hepsiburadacase.databinding.FragmentSearchBinding
import com.mehmetalivargun.hepsiburadacase.ui.DiffUtil.searchResultDiffUtil
import com.mehmetalivargun.hepsiburadacase.util.PagingLoadStateAdapter
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel: SearchViewModel by viewModels()
    private var searchAdapter: SearchAdapter = SearchAdapter(searchResultDiffUtil)

    override fun FragmentSearchBinding.initialize() {
        pagingErrorHandling()
        setAdapterClickListener()
        binding.filterRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val selected = radioGroup.findViewById<RadioButton>(i).text
            selectedEntityType = EntityType.getEntitiyBySelected(selected.toString())!!
            doSearch()
        }
    }


    //appbar search textchange listener
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_search)
        (searchItem.actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchQuery = query
                    doSearch()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    searchQuery = query
                    doSearch()
                    return true
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    fun doSearch() {
        searchQuery?.apply {
            if (length > 2) {
                viewModel.apply {
                    search(selectedEntityType, searchQuery!!).observe(viewLifecycleOwner, {
                        searchAdapter.submitData(lifecycle, it)
                        setupRecyclerView()
                    })
                }
            } else {
                binding.resultsRV.adapter = null
            }
        }
    }


    private fun pagingErrorHandling() {
        searchAdapter.addLoadStateListener { loadState ->
            binding.apply {
                if (loadState.refresh is LoadState.Loading) {
                    loadingBar.isVisible = true
                    resultsRV.isVisible = false
                    searchNotFound.isVisible = false
                    errorLayout.isVisible = false
                } else if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && searchAdapter.itemCount < 1) {
                    resultsRV.isVisible = false
                    loadingBar.isVisible = false
                    searchNotFound.isVisible = true
                    errorLayout.isVisible = false
                } else if (loadState.refresh is LoadState.Error) {
                    resultsRV.isVisible = false
                    loadingBar.isVisible = false
                    searchNotFound.isVisible = false
                    errorLayout.isVisible = true
                } else {
                    errorLayout.isVisible = false
                    resultsRV.isVisible = true
                    loadingBar.isVisible = false
                    searchNotFound.isVisible = false
                }
            }

        }

        binding.retryButton.setOnClickListener {
            doSearch()
        }
    }

    private fun setupRecyclerView() {

        with(binding) {
            binding.resultsRV.adapter = searchAdapter
            with(searchAdapter) {
                resultsRV.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }
                resultsRV.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )
            }
        }
    }

    private fun setAdapterClickListener() {
        searchAdapter.setOnItemClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                    it.kind,
                    it.trackId
                )
            )
        }
    }

    override fun onDestroy() {
        binding.resultsRV.adapter = null
        super.onDestroy()
    }

    override fun onResume() {
        //show last query results if user cameback from detail page
        doSearch()
        super.onResume()
    }

    companion object {
        var selectedEntityType: EntityType = EntityType.MOVIES
        var searchQuery: String? = ""
    }
}