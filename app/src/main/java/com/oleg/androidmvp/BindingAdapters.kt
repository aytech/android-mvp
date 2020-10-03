package com.oleg.androidmvp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.oleg.androidmvp.Configuration.Companion.TMDB_IMAGE_URL
import com.squareup.picasso.Picasso

@BindingAdapter("tmdbImage")
fun loadTmdbImage(view: ImageView, url: String?) {
    if (url != null) {
        Picasso.get()
            .load(TMDB_IMAGE_URL + url)
            .placeholder(R.drawable.ic_movie_teal_24dp)
            .error(R.drawable.ic_movie_teal_24dp)
            .into(view)
    }
}