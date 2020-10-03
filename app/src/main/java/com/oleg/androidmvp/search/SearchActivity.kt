package com.oleg.androidmvp.search

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.oleg.androidmvp.R
import com.oleg.androidmvp.model.RemoteDataSource
import com.oleg.androidmvp.model.TmdbResponse
import kotlinx.android.synthetic.main.activity_search_movie.*
import timber.log.Timber

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {

    private lateinit var adapter: SearchAdapter
    private lateinit var searchPresenter: SearchContract.PresenterInterface
    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        adapter = SearchAdapter(listOf())
        searchPresenter = SearchPresenter(this, RemoteDataSource())

        val intent = intent
        query = intent.getStringExtra(SEARCH_QUERY).toString()
    }

    override fun onStart() {
        super.onStart()
        progress_bar.visibility = VISIBLE
        searchPresenter.getSearchResults(query = query)
    }

    override fun onStop() {
        super.onStop()
        searchPresenter.stop()
    }

    override fun showToast(string: String) {
        Toast.makeText(this@SearchActivity, string, LENGTH_LONG).show()
    }

    override fun displayResult(tmdbResponse: TmdbResponse) {
        progress_bar.visibility = INVISIBLE
        if (tmdbResponse.totalResults == null || tmdbResponse.totalResults == 0) {
            search_results_recyclerview.visibility = INVISIBLE
            no_movies_text.visibility = VISIBLE
        } else {
            adapter.update(tmdbResponse.results)
            search_results_recyclerview.visibility = VISIBLE
            no_movies_text.visibility = INVISIBLE
        }
    }

    override fun displayError(error: String) {
        showToast(error)
    }

    companion object {
        const val SEARCH_QUERY = "searchQuery"
    }
}
