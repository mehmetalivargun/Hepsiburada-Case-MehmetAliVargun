package com.mehmetalivargun.hepsiburadacase.ui.search

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mehmetalivargun.hepsiburadacase.R
import com.mehmetalivargun.hepsiburadacase.base.BaseFragment
import com.mehmetalivargun.hepsiburadacase.databinding.FragmentSearchBinding
import com.mehmetalivargun.hepsiburadacase.util.PagingLoadStateAdapter
import com.mehmetalivargun.hepsiburadacase.util.constants.EntityType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel: SearchViewModel by viewModels()
    private var softwareAdapter = SoftwareAdapter()
    private var searchAdapter: SearchAdapter = SearchAdapter()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Toast.makeText(requireContext(), "Called", Toast.LENGTH_LONG).show()
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
                if (selectedEntityType == EntityType.APPS) {
                    viewModel.apply {
                        searchApp(searchQuery!!).observe(viewLifecycleOwner, {
                            softwareAdapter.submitData(lifecycle, it)
                            setupRecyclerViewSoftware()
                        })
                    }
                } else {
                    viewModel.apply {
                        search(selectedEntityType, searchQuery!!).observe(viewLifecycleOwner, {
                            searchAdapter.submitData(lifecycle, it)
                            setupRecyclerViewTrack()
                        })
                    }
                }
            } else {
                binding.resultsRV.adapter = null
            }
        }
    }

    override fun FragmentSearchBinding.initialize() {
        setupAdapters()
        setAdapterClickListener()
        binding.filterRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val selected = radioGroup.findViewById<RadioButton>(i).text
            selectedEntityType = EntityType.getEntitiyBySelected(selected.toString())!!
            doSearch()
        }
    }

    private fun setupRecyclerViewSoftware() {
        with(binding) {
            binding.resultsRV.adapter = softwareAdapter
            with(softwareAdapter) {
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
    private fun setupAdapters(){
        searchAdapter.addLoadStateListener {loadState->
            if (loadState.refresh is LoadState.Loading){
                binding.apply {
                    loadingBar.visibility= View.VISIBLE
                    resultsRV.visibility=View.GONE
                }
            }
            else{
                binding.apply {
                    loadingBar.visibility= View.GONE
                    resultsRV.visibility=View.VISIBLE
                }
            }

            val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
        }
    }
    private fun setupRecyclerViewTrack() {

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
        softwareAdapter.setOnItemClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                    it.kind, it.trackId
                )
            )
        }
    }

    override fun onDestroy() {
        binding.resultsRV.adapter = null
        super.onDestroy()
    }

    companion object {
        var selectedEntityType: EntityType = EntityType.MOVIES
        var searchQuery: String? = ""
    }
}