package com.oleg.androidmvp.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.oleg.androidmvp.BaseActivity
import com.oleg.androidmvp.Configuration.Companion.ADD_MOVIE_ACTIVITY_REQUEST_CODE
import com.oleg.androidmvp.R
import com.oleg.androidmvp.add.AddMovieActivity
import com.oleg.androidmvp.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity(), MainContract.ViewInterface {

    private lateinit var viewAdapter: MainAdapter

    private lateinit var mainPresenter: MainContract.PresenterInterface

    private fun goToAddMovieActivity() {
        val intent = Intent(this@MainActivity, AddMovieActivity::class.java)
        startActivityForResult(intent, ADD_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewAdapter = MainAdapter(listOf())
        mainPresenter = MainPresenter(this, localDataSource)
        supportActionBar?.title = "Movies to watch"
        movies_recyclerview.apply { adapter = viewAdapter }
        fab_add.setOnClickListener { goToAddMovieActivity() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.getMyMoviesList()
    }

    override fun displayError(error: Throwable) {
        error.message?.let { displayMessage(it) }
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun displayMovies(movies: List<Movie>) {
        movies_recyclerview.visibility = VISIBLE
        no_movies_layout.visibility = GONE
        viewAdapter.update(movies)
    }

    override fun displayNoMovie() {
        movies_recyclerview.visibility = GONE
        no_movies_layout.visibility = VISIBLE
    }

    override fun onRemoveSelected(size: Int) {
        viewAdapter.removeSelected()
        if (size == 1) {
            displayMessage("Movie deleted")
        } else if (size > 1) {
            displayMessage("Movies deleted")
        }
    }

    override fun onMoviesLoadComplete() {
        Timber.d("Movies loaded")
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.stop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            mainPresenter.onDeleteTapped(viewAdapter.selectedMovies)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            displayMessage("Movie successfully added.")
        } else {
            displayMessage("Movie could not be added.")
        }
    }
}