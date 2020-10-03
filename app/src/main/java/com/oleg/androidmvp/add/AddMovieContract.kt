package com.oleg.androidmvp.add

class AddMovieContract {
    interface PresenterInterface {
        fun addMovie(title: String, releaseDate: String, posterPath: String)
    }

    interface ViewInterface {
        fun returnToMain()
        fun showToast(string: String)
        fun displayError(string: String)
    }
}