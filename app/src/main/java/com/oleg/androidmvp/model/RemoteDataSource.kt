package com.oleg.androidmvp.model

import com.oleg.androidmvp.Configuration.Companion.TMDB_API_KEY
import com.oleg.androidmvp.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RemoteDataSource {
    open fun searchResultsObservable(queryString: String): Observable<TmdbResponse> {
        return RetrofitClient.moviesApi
            .searchMovie(api_key = TMDB_API_KEY, query = queryString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}