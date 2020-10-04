package com.oleg.androidmvp

class Configuration {
    companion object {
        const val ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1
        const val SEARCH_QUERY = "searchQuery"
        const val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        const val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        const val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
        const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
        const val TMDB_API_KEY = ""
        const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
        const val TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}