package com.oleg.androidmvp.add

import com.oleg.androidmvp.LocalDatabase
import com.oleg.androidmvp.model.Movie
import kotlin.concurrent.thread

class AddMoviePresenter(
    private var viewInterface: AddMovieContract.ViewInterface,
    private var localDataSource: LocalDatabase
) : AddMovieContract.PresenterInterface {

    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            viewInterface.displayError("Movie title cannot be empty")
        } else {
            thread {
                localDataSource.movieDao().insert(Movie(title, releaseDate, posterPath))
            }
            viewInterface.returnToMain()
        }
    }
}