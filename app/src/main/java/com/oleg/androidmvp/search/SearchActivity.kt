package com.oleg.androidmvp.search

import android.content.Intent
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.oleg.androidmvp.Configuration.Companion.EXTRA_POSTER_PATH
import com.oleg.androidmvp.Configuration.Companion.EXTRA_RELEASE_DATE
import com.oleg.androidmvp.Configuration.Companion.EXTRA_TITLE
import com.oleg.androidmvp.Configuration.Companion.SEARCH_QUERY
import com.oleg.androidmvp.R
import com.oleg.androidmvp.model.Movie
import com.oleg.androidmvp.model.RemoteDataSource
import com.oleg.androidmvp.model.TmdbResponse
import kotlinx.android.synthetic.main.activity_search_movie.*

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {

    private lateinit var viewAdapter: SearchAdapter
    private lateinit var searchPresenter: SearchContract.PresenterInterface
    private lateinit var query: String

    interface RecyclerItemListener {
        fun onItemClick(movie: Movie)
    }

    private var movieClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(movie: Movie) {

            val intent = Intent()

            intent.putExtra(EXTRA_TITLE, movie.title)
            intent.putExtra(EXTRA_RELEASE_DATE, movie.releaseDate)
            intent.putExtra(EXTRA_POSTER_PATH, movie.posterPath)
            setResult(RESULT_OK, intent)

            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)

        viewAdapter = SearchAdapter(listOf(), movieClickListener)
        searchPresenter = SearchPresenter(this, RemoteDataSource())

        val intent = intent
        query = intent.getStringExtra(SEARCH_QUERY).toString()

        search_results_recyclerview.apply { adapter = viewAdapter }
    }

    override fun onStart() {
        super.onStart()
        progress_bar.visibility = GONE

        val movies = ArrayList<Movie>()
        movies.add(Movie("Title1", "2020-09-12", "/9MaAzW8sERDcIEYORE7jzeCew3P.jpg"))
        movies.add(Movie("Title2", "2020-09-12", "/9MaAzW8sERDcIEYORE7jzeCew3P.jpg"))
        viewAdapter.update(movies)
        // searchPresenter.getSearchResults(query = query)
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
            search_results_recyclerview.visibility = GONE
            no_movies_text.visibility = VISIBLE
        } else {
            search_results_recyclerview.visibility = VISIBLE
            no_movies_text.visibility = GONE
            viewAdapter.update(tmdbResponse.results)
        }
    }

    override fun displayError(error: String) {
        showToast(error)
    }
}
