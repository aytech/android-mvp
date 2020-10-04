package com.oleg.androidmvp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oleg.androidmvp.databinding.ItemMovieMainBinding
import com.oleg.androidmvp.model.Movie
import kotlinx.android.synthetic.main.item_movie_main.view.*

class MainAdapter(private var movieModels: List<MainViewModel>) :
    RecyclerView.Adapter<MainAdapter.MoviesHolder>() {

    private lateinit var movies: List<Movie>
    internal val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieMainBinding.inflate(inflater, parent, false)
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) =
        holder.bind(movieModel = movieModels[position])

    override fun getItemCount(): Int = movieModels.size

    private fun selectMovie(position: Int) {
        val movie = movies[position]
        if (selectedMovies.contains(movie)) {
            selectedMovies.remove(movie)
        } else {
            selectedMovies.add(movie)
        }
    }

    fun update(movies: List<Movie>) {
        this.movieModels = movies.map {
            MainViewModel(
                title = it.title,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                checked = false
            )
        }
        this.movies = movies
        notifyDataSetChanged()
    }

    fun removeSelected() {
        selectedMovies.clear()
    }

    inner class MoviesHolder(private val binding: ItemMovieMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieModel: MainViewModel) {
            binding.movie = movieModel
            binding.root.checkbox.setOnClickListener { selectMovie(adapterPosition) }
        }
    }
}
