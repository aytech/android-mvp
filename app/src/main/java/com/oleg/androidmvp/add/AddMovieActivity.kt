package com.oleg.androidmvp.add

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.oleg.androidmvp.R
import com.oleg.androidmvp.model.LocalDataSource
import com.oleg.androidmvp.search.SearchActivity
import kotlinx.android.synthetic.main.activity_add_movie.*
import timber.log.Timber

class AddMovieActivity : AppCompatActivity(), AddMovieContract.ViewInterface {

    private lateinit var addMoviePresenter: AddMovieContract.PresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        addMoviePresenter = AddMoviePresenter(this, LocalDataSource(application))
    }

    override fun returnToMain() {
        TODO("Not yet implemented")
    }

    override fun showToast(string: String) {
        TODO("Not yet implemented")
    }

    override fun displayError(string: String) {
        TODO("Not yet implemented")
    }

    fun goToSearchMovieActivity(view: View) {
        val intent = Intent(this@AddMovieActivity, SearchActivity::class.java)
        intent.putExtra(SearchActivity.SEARCH_QUERY, movie_title.text.toString())
        startActivityForResult(intent, SEARCH_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    fun onClickAddMovie(view: View) {}

    companion object {
        const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
    }
}