package com.oleg.androidmvp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oleg.androidmvp.databinding.ItemMovieMainBinding
import com.oleg.androidmvp.model.Movie

class MainAdapter(private var movies: List<MainViewModel>) :
    RecyclerView.Adapter<MainAdapter.MoviesHolder>() {

    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieMainBinding.inflate(inflater)
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) =
        holder.bind(movie = movies[position])

    override fun getItemCount(): Int = movies.size

    private fun selectMovie(movie: Movie) {
        if (selectedMovies.contains(movie)) {
            selectedMovies.remove(movie)
        } else {
            selectedMovies.add(movie)
        }
    }

    fun update(movies: List<Movie>) {
        this.movies = movies.map {
            MainViewModel(
                title = it.title,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                onClick = { selectMovie(it) }
            )
        }
        notifyDataSetChanged()
    }

    inner class MoviesHolder(private val binding: ItemMovieMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MainViewModel) {
            binding.movie = movie
        }
    }
}
