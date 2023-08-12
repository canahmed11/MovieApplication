package com.ahmeturunveren.movieapp.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmeturunveren.movieapp.data.model.movie.MovieModel
import com.ahmeturunveren.movieapp.databinding.SearchItemBinding
import com.ahmeturunveren.movieapp.util.diffutil.DiffUtilCallback

class SearchAdapter(private var onSearchClick: (MovieModel) -> Unit) :
    ListAdapter<MovieModel, SearchAdapter.SearchViewHolder>(
        DiffUtilCallback<MovieModel>(
            itemsTheSame = { oldItem, newItem ->
                oldItem == newItem
            },
            contentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SearchViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel) = with(binding) {
            movieTitle.text = movie.title
            movieDate.text = movie.releaseDate
            root.setOnClickListener {
                onSearchClick(movie)
            }
        }
    }
}