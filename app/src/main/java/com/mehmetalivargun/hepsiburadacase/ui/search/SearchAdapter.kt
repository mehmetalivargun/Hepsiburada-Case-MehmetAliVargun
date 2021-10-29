package com.mehmetalivargun.hepsiburadacase.ui.search

import com.mehmetalivargun.hepsiburadacase.base.BaseSearchAdapter
import com.mehmetalivargun.hepsiburadacase.data.model.SearchTrackItem
import com.mehmetalivargun.hepsiburadacase.ui.DiffUtil.searchResultDiffUtil

class SearchAdapter : BaseSearchAdapter<SearchTrackItem>(searchResultDiffUtil){

}