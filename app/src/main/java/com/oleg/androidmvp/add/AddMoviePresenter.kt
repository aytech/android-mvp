package com.oleg.androidmvp.add

import com.oleg.androidmvp.model.LocalDataSource
import com.oleg.androidmvp.model.Movie

class AddMoviePresenter(private var viewInterface: AddMovieContract.ViewInterface, private var dataSource: LocalDataSource) : AddMovieContract.PresenterInterface {
    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            viewInterface.displayError("Movie title cannot be empty")
        } else {
            dataSource.insert(Movie(title, releaseDate, posterPath))
            viewInterface.returnToMain()
        }
    }
}