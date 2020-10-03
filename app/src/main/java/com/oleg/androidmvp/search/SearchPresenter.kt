package com.oleg.androidmvp.search

import com.oleg.androidmvp.model.RemoteDataSource
import com.oleg.androidmvp.model.TmdbResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchPresenter(
    private var viewInterface: SearchContract.ViewInterface,
    private var dataSource: RemoteDataSource
) : SearchContract.PresenterInterface {

    private val compositeDisposable = CompositeDisposable()

    val searchResultObservable: (String) -> Observable<TmdbResponse> =
        { query -> dataSource.searchResultsObservable(query) }
    private val observer: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>() {
            override fun onNext(@NonNull response: TmdbResponse) {
                Timber.d("Got response: %s", response)
                viewInterface.displayResult(response)
            }

            override fun onError(error: Throwable) {
                Timber.d("Error fetching movies: $error")
                viewInterface.displayError("Error fetching movies")
            }

            override fun onComplete() {
                Timber.d("Fetch movies complete")
            }
        }

    override fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(searchResultsDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}