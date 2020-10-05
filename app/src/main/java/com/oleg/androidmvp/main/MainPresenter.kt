package com.oleg.androidmvp.main

import com.oleg.androidmvp.LocalDatabase
import com.oleg.androidmvp.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var localDataSource: LocalDatabase
) : MainContract.PresenterInterface {

    private val compositeDisposable = CompositeDisposable()

    private val myMovieObservable: Observable<List<Movie>>
        get() = localDataSource.movieDao().all
    private val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {
            override fun onNext(movies: List<Movie>) {
                Timber.d("Got movies: $movies")
                if (movies.isEmpty()) {
                    viewInterface.displayNoMovie()
                } else {
                    viewInterface.displayMovies(movies)
                }
            }

            override fun onError(error: Throwable) {
                Timber.d("Error: $error")
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
        if (selectedMovies.size >= 1) {
            for (movie in selectedMovies) {
                localDataSource.movieDao().delete(movie.id)
            }
            viewInterface.onRemoveSelected(selectedMovies.size)
        } else {
            viewInterface.displayMessage("Select a movie")
        }
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}
