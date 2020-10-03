package com.oleg.androidmvp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TmdbResponse
/**
 *
 * @param results
 * @param totalResults
 * @param page
 * @param totalPages
 */(
    @SerializedName("page")
    @Expose var page: Int?, @SerializedName("total_results")
    @Expose var totalResults: Int?, @SerializedName("total_pages")
    @Expose var totalPages: Int?, results: List<Movie>
) {

    @SerializedName("results")
    @Expose
    var results: List<Movie>? = results

}