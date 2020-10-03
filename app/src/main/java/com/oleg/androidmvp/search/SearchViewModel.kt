package com.oleg.androidmvp.search

data class SearchViewModel(
    val title: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val onClick: () -> Unit
)