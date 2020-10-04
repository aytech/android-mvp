package com.oleg.androidmvp.model

import android.app.Application
import io.reactivex.Observable
import kotlin.concurrent.thread

class LocalDataSource(application: Application) {

    private val movieDao: MovieDao = LocalDatabase.getInstance(application).movieDao()

    fun getAll(): Observable<List<Movie>> {
        return movieDao.all
    }

    fun insert(movie: Movie) {
        thread {
            movieDao.insert(movie)
        }
    }

    fun delete(movie: Movie) {
        thread {
            movieDao.delete(movie.id)
        }
    }
}