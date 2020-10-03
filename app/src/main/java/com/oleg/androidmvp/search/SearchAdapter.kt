package com.oleg.androidmvp.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oleg.androidmvp.databinding.ItemMovieDetailsBinding
import com.oleg.androidmvp.model.Movie

class SearchAdapter(
    private var movies: List<SearchViewModel>,
    private var listener: SearchActivity.RecyclerItemListener
) :
    RecyclerView.Adapter<SearchAdapter.SearchMoviesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieDetailsBinding.inflate(inflater, parent, false)
        return SearchMoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchMoviesHolder, position: Int) =
        holder.bind(model = movies[position])

    override fun getItemCount(): Int = movies.size

    private fun onMovieClicked(movie: Movie) {
        listener.onItemClick(movie)
    }

    fun update(movies: List<Movie>?) {
        this.movies = movies!!.map {
            SearchViewModel(
                title = it.title,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                onClick = { onMovieClicked(it) }
            )
        }
        notifyDataSetChanged()
    }

    inner class SearchMoviesHolder(private val binding: ItemMovieDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SearchViewModel) {
            binding.movie = model
            binding.root.setOnClickListener { model.onClick() }
        }
    }
}
