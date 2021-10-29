package com.mehmetalivargun.hepsiburadacase.ui.search

import com.mehmetalivargun.hepsiburadacase.base.BaseSearchAdapter
import com.mehmetalivargun.hepsiburadacase.data.model.SearchSoftwareItem
import com.mehmetalivargun.hepsiburadacase.ui.DiffUtil

class SoftwareAdapter : BaseSearchAdapter<SearchSoftwareItem>(DiffUtil.searchAppDiffUtil){

}