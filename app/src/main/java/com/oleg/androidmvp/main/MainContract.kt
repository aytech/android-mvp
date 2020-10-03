package com.oleg.androidmvp.main

import com.oleg.androidmvp.model.Movie

class MainContract {
    interface PresenterInterface {
        fun getMyMoviesList()
        fun onDeleteTapped(selectedMovies: HashSet<Movie>)
        fun stop()
    }
    interface ViewInterface {
        fun displayError(error: Throwable)
        fun displayMessage(message: String)
        fun displayMovies(movies: List<Movie>)
        fun displayNoMovie()
        fun onMoviesLoadComplete()
    }
}
