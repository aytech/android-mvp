package com.oleg.androidmvp.search

import com.oleg.androidmvp.model.TmdbResponse

class SearchContract {
    interface PresenterInterface {
        fun getSearchResults(query: String)
        fun stop()
    }

    interface ViewInterface {
        fun displayError(error: String)
        fun displayResult(tmdbResponse: TmdbResponse)
        fun showToast(string: String)
    }
}