package com.oleg.androidmvp.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.oleg.androidmvp.R
import com.oleg.androidmvp.add.AddMovieActivity
import com.oleg.androidmvp.model.LocalDataSource
import com.oleg.androidmvp.model.Movie

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var adapter: MainAdapter

    private lateinit var mainPresenter: MainContract.PresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(listOf())
        mainPresenter = MainPresenter(this, LocalDataSource(application))
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.getMyMoviesList()
    }

    override fun displayError(error: Throwable) {
        // TODO: "Not yet implemented"
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun displayMovies(movies: List<Movie>) {
        adapter.update(movies)
    }

    override fun displayNoMovie() {
        // TODO: "Not yet implemented"
    }

    override fun onMoviesLoadComplete() {
        // TODO: "Not yet implemented"
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.stop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            mainPresenter.onDeleteTapped(adapter.selectedMovies)
        }
        return super.onOptionsItemSelected(item)
    }

    fun goToAddMovieActivity(view: View) {
        val intent = Intent(this@MainActivity, AddMovieActivity::class.java)
        startActivityForResult(intent, ADD_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    companion object {
        const val ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1
    }
}