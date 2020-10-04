package com.oleg.androidmvp.main

import com.oleg.androidmvp.model.LocalDataSource
import com.oleg.androidmvp.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var dataSource: LocalDataSource
) : MainContract.PresenterInterface {

    private val compositeDisposable = CompositeDisposable()

    private val myMovieObservable: Observable<List<Movie>>
        get() = dataSource.getAll()
    private val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {
            override fun onNext(movies: List<Movie>) {
                if (movies.isEmpty()) {
                    viewInterface.displayNoMovie()
                } else {
                    viewInterface.displayMovies(movies)
                }
            }

            override fun onError(error: Throwable) {
                viewInterface.displayError(error)
            }

            override fun onComplete() {
                viewInterface.onMoviesLoadComplete()
            }
        }

    override fun getMyMoviesList() {
        val myMoviesDisposable = myMovieObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(myMoviesDisposable)
    }

    override fun onDeleteTapped(selectedMovies: HashSet<Movie>) {
        if (selectedMovies.size < 1) {
            viewInterface.displayMessage("Select a movie")
        }
        for (movie in selectedMovies) {
            dataSource.delete(movie)
        }
        viewInterface.onRemoveSelected(selectedMovies.size)
    }

    override fun stop() {
        compositeDisposable.clear()
    }

}
